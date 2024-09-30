/*
package com.wny.schoolbus.configs;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.wny.schoolbus.security.AuthenticationEntryPointHandler;
import com.wny.schoolbus.security.AuthenticationTokenFilter;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    private final AuthenticationTokenFilter authenticationTokenFilter;
    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ к статическим ресурсам Vaadin и другим статическим файлам
                        .requestMatchers(
                                "/VAADIN/**",    // Статические ресурсы Vaadin
                                "/frontend/**",  // Фронтенд-файлы Vaadin
                                "/webjars/**",   // Webjars
                                "/images/**",    // Изображения
                                "/css/**",       // CSS файлы
                                "/js/**",        // JavaScript файлы
                                "/favicon.ico",  // Иконка сайта
                                "/index.html"    // Главная страница
                        ).permitAll()
                        // Разрешаем доступ к API для аутентификации и регистрации без авторизации
                        .requestMatchers(
                                "/services/v3/user/**",
                                "/services/v3/registration/**",
                                "/services/v3/authentication/**"
                        ).permitAll()
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                // Отключаем сессии (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Отключаем CSRF для REST API
                .csrf(csrf -> csrf.disable())
                // Настраиваем CORS (если это необходимо)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        // Добавляем кастомный фильтр для аутентификации перед базовой аутентификацией
        http.addFilterBefore(authenticationTokenFilter, BasicAuthenticationFilter.class);

        // Настраиваем обработчик ошибок аутентификации
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPointHandler));

        return http.build();
    }



 */
/*   @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll())  // Разрешить все запросы
                .csrf(AbstractHttpConfigurer::disable)  // Отключить CSRF
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Уберите фильтры авторизации, если они все еще активны
        // http.addFilterBefore(authenticationTokenFilter, BasicAuthenticationFilter.class);
        // http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPointHandler));

        return http.build();
    }*//*


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Backside-Transport", "Content-Type", "Authorization",
                "X-Requested-With", "Content-Length", "Accept", "Origin", "Location", "Accept-Language"));
        configuration.setExposedHeaders(Arrays.asList("X-Backside-Transport", "Content-Type", "Authorization",
                "X-Requested-With", "Content-Length", "Accept", "Origin", "Location", "Accept-Language"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}*/
