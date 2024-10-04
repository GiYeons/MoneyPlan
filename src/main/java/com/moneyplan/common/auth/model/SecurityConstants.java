package com.moneyplan.common.auth.model;

public class SecurityConstants {

    public static final String[] PERMITTED_URI = {
        "/h2-console/**","/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
        "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/api/v1/members/**",
        "/favicon.ico/**"
    };

}
