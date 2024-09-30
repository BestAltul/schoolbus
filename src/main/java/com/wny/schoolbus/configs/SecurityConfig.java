package com.wny.schoolbus.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Разрешаем доступ ко всем запросам
                )
                .csrf(csrf -> csrf.disable());  // Отключаем CSRF с использованием лямбда-выражения

        return http.build();
    }
}
