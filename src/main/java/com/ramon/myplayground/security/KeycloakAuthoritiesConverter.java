package com.ramon.myplayground.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KeycloakAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String ROLE_PREFIX = "ROLE_";
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String CLIENT_NAME = "car-api";
    private static final String ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(@NonNull final Jwt jwt) {
        return extractRealmRoles(jwt)
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Stream<String> extractRealmRoles(final Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsMap(RESOURCE_ACCESS))
                .map(resource -> (Map<String, Collection<String>>) resource.get(CLIENT_NAME))
                .map(resource -> resource.get(ROLES))
                .orElse(Collections.emptyList())
                .stream();
    }
}
