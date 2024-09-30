package com.wny.schoolbus.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

@UtilityClass
public class Constant {
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final String SALT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public final Set<String> excludedUrls = Set.of(
            "/error", "/v3/api-docs.yaml", "/health", "/v3/api-docs", "/v3/api-docs/swagger-config", "/swagger-ui.html"
    );

    public static final List<String> noNeedFit = List.of(
            //swagger
            "/swagger-ui",
            "/swagger-config",
            "/api-docs",
            "/registration",
            "/services/v3/authentication/login",
            "/bus-list"

    );
}
