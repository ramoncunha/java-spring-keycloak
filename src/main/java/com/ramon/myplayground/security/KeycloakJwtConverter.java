package com.ramon.myplayground.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final KeycloakAuthoritiesConverter authoritiesConverter;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        return new JwtAuthenticationToken(jwt, extractAuthorities(jwt), extractPrincipal(jwt));
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(final Jwt jwt) {
        return authoritiesConverter.convert(jwt);
    }

    private String extractPrincipal(final Jwt jwt) {
        return jwt.getClaimAsString(JwtClaimNames.SUB);
    }
}
