package it.unisalento.pas.wastedisposalagencybe.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Questo convertitore converte un oggetto Jwt in un oggetto AbstractAuthenticationToken.
 * Estrae il ruolo (claim "role") dal token JWT e crea un oggetto SimpleGrantedAuthority.
 * Utilizzato per l'autenticazione JWT.
 */
public class JwtTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String CLAIM_ROLE = "role"; // Chiave per il claim del ruolo nel JWT

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractRole(jwt.getClaims());
        return new JwtAuthenticationToken(jwt, authorities);
    }

    /**
     * Estrae il ruolo dal claim del token JWT e lo converte in un oggetto GrantedAuthority.
     *
     * @param claims Map contenente i claims del JWT
     * @return Una collezione di autorizzazioni (GrantedAuthority)
     */
    private Collection<GrantedAuthority> extractRole(Map<String, Object> claims) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (claims.containsKey(CLAIM_ROLE)) {
            String role = (String) claims.get(CLAIM_ROLE);
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
