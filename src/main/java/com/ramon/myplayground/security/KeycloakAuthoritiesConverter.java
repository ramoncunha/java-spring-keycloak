package com.ramon.myplayground.security;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String ROLE_PREFIX = "ROLE_";
    private static final String ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(@NonNull final Jwt jwt) {
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
