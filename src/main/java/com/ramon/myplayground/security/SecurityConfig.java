package com.ramon.myplayground.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ROLE_ADMIN = "CARAPI_ADMIN";
    private static final String ROLE_CREATE = "CARAPI_CREATE";
    private static final String ROLE_UPDATE = "CARAPI_UPDATE";
    private static final String ROLE_DELETE = "CARAPI_DELETE";

    private final KeycloakJwtConverter keycloakJwtConverter;

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
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakJwtConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
