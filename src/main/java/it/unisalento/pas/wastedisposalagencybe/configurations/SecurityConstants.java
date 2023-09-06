package it.unisalento.pas.wastedisposalagencybe.configurations;

/**
 * Classe contenente costanti utilizzate per la configurazione della sicurezza.
 */
public class SecurityConstants {
    // Lista degli issuer per i token JWT
    public static final String[] ISSUER_LIST = {"https://smart-city-waste-management.eu.auth0.com/"};

    // Ruoli degli utenti
    public static final String OPERATOR_ROLE_ID = "OPERATOR"; // Ruolo per gli operatori
    public static final String USER_ROLE_ID = "USER";         // Ruolo per gli utenti
    public static final String ADMIN_ROLE_ID = "ADMIN";       // Ruolo per gli amministratori
}
