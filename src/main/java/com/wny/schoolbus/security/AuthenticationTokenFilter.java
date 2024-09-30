/*
package com.wny.schoolbus.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.wny.schoolbus.enums.ApiErrorCode;
import com.wny.schoolbus.exceptions.ApiError;
import com.wny.schoolbus.exceptions.AuthenticationException;
import com.wny.schoolbus.exceptions.JwtException;
import com.wny.schoolbus.services.UserCredentialsDetailService;
import com.wny.schoolbus.utils.Constant;
import com.wny.schoolbus.utils.JWTUtil;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final UserCredentialsDetailService userCredentialsDetailService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var requestURL = request.getRequestURL().toString();

        var requestURI = request.getRequestURI();

        // Пропускаем запросы к статическим ресурсам и другим разрешённым URL
        if (requestURI.equals("/favicon.ico") ||
                requestURI.startsWith("/VAADIN/") ||
                requestURI.startsWith("/frontend/") ||
                requestURI.startsWith("/webjars/") ||
                requestURI.startsWith("/images/") ||
                requestURI.startsWith("/css/") ||
                requestURI.startsWith("/js/") ||
                requestURI.equals("/bus-list")) {

            filterChain.doFilter(request, response);  // Пропускаем запрос без аутентификации
            return;
        }


        String authHeader = request.getHeader("Authorization");

        var needFit = Constant.noNeedFit.stream().noneMatch(requestURI::contains);

        try {
            if (needFit) {
                if (Objects.isNull(authHeader)) {
                    throw new JwtException(
                            ApiErrorCode.AUTHENTICATION_ERROR,
                            "Authorization header is null"
                    );
                } else {
                    String jwt = authHeader.substring(7);
                    String userlogin = jwtUtil.validateToken(jwt);

                    UserDetails userDetails = userCredentialsDetailService.loadUserByUsername(userlogin);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (AuthenticationException exception) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    ApiError.builder()
                            .code(exception.getApiErrorCode())
                            .status(HttpStatus.UNAUTHORIZED)
                            .message(exception.getMessage())
                            .build()
            ));
            return;
        } catch (JwtException exception) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                            ApiError.builder()
                                    .code(exception.getApiErrorCode())
                                    .status(HttpStatus.UNAUTHORIZED)
                                    .message(exception.getMessage())
                                    .build()
                    )
            );
            return;


        }
        filterChain.doFilter(request, response);
    }
}
*/
