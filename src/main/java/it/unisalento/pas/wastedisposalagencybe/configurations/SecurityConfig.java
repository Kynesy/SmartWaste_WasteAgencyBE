package it.unisalento.pas.wastedisposalagencybe.configurations;

import it.unisalento.pas.wastedisposalagencybe.security.JwtTokenConverter;
import it.unisalento.pas.wastedisposalagencybe.security.JwtTokenValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configurazione della sicurezza per l'applicazione.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenConverter jwtTokenConverter = new JwtTokenConverter();

    /**
     * Configura le regole di sicurezza per le richieste HTTP.
     *
     * @param http Oggetto HttpSecurity da configurare
     * @return SecurityFilterChain configurato
     * @throws Exception Eccezione in caso di errori di configurazione
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });
        http.csrf(AbstractHttpConfigurer::disable);
        // Solo gli utenti autorizzati possono accedere alle API
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        // UserController
                        .requestMatchers(HttpMethod.GET, "/api/user/exist/{userID}").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.POST, "/api/user/create").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.POST, "/api/user/update").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.DELETE, "/api/user/delete/{userID}").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.GET, "/api/user/get/{userID}").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)

                        // BinController
                        .requestMatchers(HttpMethod.POST, "/api/bin/create").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.POST, "/api/bin/update").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.DELETE, "/api/bin/delete/{binID}").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.GET, "/api/bin/get/{binID}").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)
                        .requestMatchers(HttpMethod.GET, "/api/bin/get/all").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)

                        // TrashController
                        .requestMatchers(HttpMethod.GET, "/api/trash/notifications/user/{userID}").hasAnyAuthority(SecurityConstants.USER_ROLE_ID)
                        .requestMatchers(HttpMethod.GET, "/api/trash/statistics/user/{userID}/{year}").hasAnyAuthority(SecurityConstants.USER_ROLE_ID, SecurityConstants.ADMIN_ROLE_ID)
                        .requestMatchers(HttpMethod.POST, "/api/trash/statistics/user/all/{year}").hasAnyAuthority(SecurityConstants.ADMIN_ROLE_ID)
                        .requestMatchers(HttpMethod.GET, "/api/trash/statistics/city/{year}").hasAnyAuthority(SecurityConstants.ADMIN_ROLE_ID)

                        // AlertController
                        .requestMatchers(HttpMethod.GET, "/api/alert/get/all").hasAnyAuthority(SecurityConstants.OPERATOR_ROLE_ID)

                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtTokenConverter)
                ))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    /**
     * Configura le impostazioni CORS (Cross-Origin Resource Sharing).
     *
     * @return Oggetto CorsConfigurationSource configurato
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Configura il decodificatore JWT.
     *
     * @return Oggetto JwtDecoder configurato
     */
    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(SecurityConstants.ISSUER_LIST[0]);
        OAuth2TokenValidator<Jwt> tokenValidator = new JwtTokenValidator();
        jwtDecoder.setJwtValidator(tokenValidator);
        return jwtDecoder;
    }
}
