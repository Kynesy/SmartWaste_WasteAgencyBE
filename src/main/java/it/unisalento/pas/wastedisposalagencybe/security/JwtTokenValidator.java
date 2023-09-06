package it.unisalento.pas.wastedisposalagencybe.security;

import it.unisalento.pas.wastedisposalagencybe.configurations.SecurityConstants;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

/**
 * Questo validator verifica la validità di un token JWT.
 */
public class JwtTokenValidator implements OAuth2TokenValidator<Jwt> {
    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (isTokenExpired(jwt)) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "Token expired", null));
        }

        if (!isValidIssuer(jwt)) {
            return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "Invalid issuer", null));
        }

        return OAuth2TokenValidatorResult.success();
    }

    /**
     * Verifica se il token JWT è scaduto.
     *
     * @param jwt Token JWT da verificare
     * @return true se il token è scaduto, false altrimenti
     */
    private boolean isTokenExpired(Jwt jwt) {
        Instant tokenExpiration = jwt.getExpiresAt();
        return tokenExpiration != null && tokenExpiration.isBefore(Instant.now());
    }

    /**
     * Verifica se l'emittente (issuer) del token JWT è valido confrontando con una lista di issuer attendibili.
     *
     * @param jwt Token JWT da verificare
     * @return true se l'emittente è valido, false altrimenti
     */
    private boolean isValidIssuer(Jwt jwt) {
        String tokenIssuer = String.valueOf(jwt.getIssuer());
        String[] expectedIssuer = SecurityConstants.ISSUER_LIST;
        for (String issuer : expectedIssuer) {
            if (issuer.equals(tokenIssuer)) {
                return true; // Trovato un emittente corrispondente nella lista
            }
        }

        return false; // Nessun emittente corrispondente trovato
    }
}
