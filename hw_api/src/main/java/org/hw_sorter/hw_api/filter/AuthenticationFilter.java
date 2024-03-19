package org.hw_sorter.hw_api.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.config.jwt.JwtConfig;
import org.hw_sorter.hw_api.global.ApiCallerInformation;
import org.hw_sorter.hw_api.global.exception.JwtExpiredException;
import org.hw_sorter.hw_api.global.exception.JwtValidationFailException;
import org.hw_sorter.hw_api.response.FailResponse;
import org.hw_sorter.hw_api.response.ResponseFactory;
import org.hw_sorter.service.user.dto.UserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final ResponseFactory responseFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("uri : {}", request.getRequestURI());

        if (accessToken == null) {
            FailResponse<?> failResponse = FailResponse.builder()
                    .data(null)
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .errorMsg("no access token")
                    .build();
            this.setFailResponse(response, failResponse);
            return;
        }

        // Bearer
        String jwt = accessToken.substring(7);

        ApiCallerInformation apiCaller = null;
        try {
//            JwsHeader<?> jwtHeader = JwtConfig.getHeader(jwt);
//            JwtConfig.JwtType jwtType = JwtConfig.JwtType.valueOf((String) jwtHeader.get("jwtType"));
            UserResponse user = JwtConfig.getPayloadFromJwt(jwt, "user", UserResponse.class);
            apiCaller = new ApiCallerInformation(user);
        } catch (JwtExpiredException | JwtValidationFailException e) {
            FailResponse<?> failResponse = FailResponse.builder()
                    .data(null)
                    .status(
                            e instanceof JwtValidationFailException ?
                                    HttpStatus.BAD_REQUEST.value() :
                                    HttpStatus.UNAUTHORIZED.value()
                    )
                    .errorMsg("access token malformed")
                    .build();

            this.setFailResponse(response, failResponse);
            return;
        }

        request.setAttribute("jwt-payload", apiCaller);
        filterChain.doFilter(request, response);
    }

    private void setFailResponse(HttpServletResponse response, FailResponse<?> failResponse) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(failResponse.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(failResponse.toString());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {
                "/api-docs",
                "/swagger",
                "/api/user/login"
        };

        String path = request.getRequestURI();

        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
