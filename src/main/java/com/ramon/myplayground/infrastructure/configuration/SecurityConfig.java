package com.ramon.myplayground.infrastructure.configuration;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public static final String ROLE_PREFIX = "ROLE_";
    private static final String ROLE_ADMIN = "CARAPI_ADMIN";
    private static final String ROLE_CREATE = "CARAPI_CREATE";
    private static final String ROLE_UPDATE = "CARAPI_UPDATE";
    private static final String ROLE_DELETE = "CARAPI_DELETE";
    private static final String ROLES = "roles";

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/cars").hasAnyRole(ROLE_CREATE)
                        .requestMatchers(HttpMethod.PUT, "/cars/*").hasAnyRole(ROLE_UPDATE)
                        .requestMatchers(HttpMethod.DELETE, "/cars/*").hasAnyRole(ROLE_DELETE)
                        .requestMatchers(HttpMethod.GET, "/cars").permitAll()
                        .requestMatchers("/users").permitAll()
                        .anyRequest().hasRole(ROLE_ADMIN))
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtConverter())))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    static class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        private final KeycloakAuthoritiesConverter authoritiesConverter;

        public KeycloakJwtConverter() {
            authoritiesConverter = new KeycloakAuthoritiesConverter();
        }

        @Override
        public AbstractAuthenticationToken convert(Jwt jwt) {
            return new JwtAuthenticationToken(jwt, extractAuthorities(jwt), extractPrincipal(jwt));
        }

        private Collection<? extends GrantedAuthority> extractAuthorities(final Jwt jwt) {
            return authoritiesConverter.convert(jwt);
        }

        private String extractPrincipal(final Jwt jwt) {
            return jwt.getClaimAsString(JwtClaimNames.SUB);
        }
    }

    static class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        @Override
        public Collection<GrantedAuthority> convert(final Jwt jwt) {
            final var realmRoles = extractRealmRoles(jwt);
            final var resourceRoles = extractResourceRoles(jwt);
            return Stream.concat(realmRoles, resourceRoles)
                    .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
                    .collect(Collectors.toSet());
        }

        @SuppressWarnings("unchecked")
        private Stream<String> extractRealmRoles(final Jwt jwt) {
            return Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
                    .map(resource -> (Collection<String>) resource.get(ROLES))
                    .orElse(Collections.emptyList())
                    .stream();
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        private Stream<String> extractResourceRoles(final Jwt jwt) {
            final Function<Map.Entry<String, Object>, Stream<String>> mapResources =
                    resource -> {
                        final var key = resource.getKey();
                        final var value = (LinkedTreeMap) resource.getValue();
                        final var roles = (Collection<String>) value.get(ROLES);
                        return roles.stream().map(role -> key.concat("_").concat(role));
                    };

            final Function<Set<Map.Entry<String, Object>>, Collection<String>> mapResource =
                    resources -> resources.stream()
                            .flatMap(mapResources)
                            .toList();

            return Optional.ofNullable(jwt.getClaimAsMap("resource_access"))
                    .map(Map::entrySet)
                    .map(mapResource)
                    .orElse(Collections.emptyList())
                    .stream();
        }
    }
}
