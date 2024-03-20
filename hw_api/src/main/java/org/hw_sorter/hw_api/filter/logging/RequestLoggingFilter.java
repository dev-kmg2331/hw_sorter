package org.hw_sorter.hw_api.filter.logging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();


    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.info(message);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }

        long start = System.currentTimeMillis();

        if (isFirstRequest && isIncludeClientInfo()) {
            beforeRequest(requestToUse, getBeforeMessage(requestToUse));
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                afterRequest(requestToUse, getAfterMessage(System.currentTimeMillis() - start, response.getStatus()));
            }
        }
    }

    private String getBeforeMessage(HttpServletRequest request) {
        return createMessage(request, "\n", "");
    }

    private String getAfterMessage(long elapsed, int status) {
        return "Response : [" + elapsed + "ms, " + status + "]";
    }

    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append("[ ").append(request.getMethod()).append(" ]").append(' ');
        msg.append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append('?').append(queryString);
            }
        }

        msg.append("\n");

        if (isIncludeClientInfo()) {
            String client = request.getHeader("X-FORWARDED-FOR");
            /*log.info("client : {}", client);*/
            log.info("x-forwarded-for : " + client);
            if (StringUtils.hasLength(client)) {
                msg.append(", ip=").append(client);
//                try {
//                    if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
//                        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//                        if (Optional.ofNullable(jwtToken).isEmpty()) {
//
//                        } else {
//                            String splitJwtToken = jwtToken.split(" ")[1];
//                            UserResponse userResponse = JwtManagement.getPayloadFromJwt(splitJwtToken, "user", UserResponse.class);
//                            msg.append(", client=").append(userResponse.getUsername());
//                        }
//                    }
//                } catch (JwtValidationFailException e) {
//                    log.warn("jwt validation failed");
//                }
//                msg.append(", client=").append(getIp(clientLocalIp));
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append(", session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append(", user=").append(user);
            }
        }

        msg.append("\n");

        if (isIncludeHeaders()) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            if (getHeaderPredicate() != null) {
                Enumeration<String> names = request.getHeaderNames();
                while (names.hasMoreElements()) {
                    String header = names.nextElement();
                    if (!getHeaderPredicate().test(header)) {
                        headers.set(header, "masked");
                    }
                }
            }
            msg.append(", headers=").append(headers);
        }

        if (isIncludePayload()) {
            String payload = null;

            try {
                payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);


            } catch (IOException e) {
                log.error("failed to read payload", e);
            }
            if (payload != null) {
//                try {
//                    if (payload.startsWith("Bearer")) {
//                        String splitJwtToken = payload.split(" ")[1];
//                        UserResponse userResponse = JwtManagement.getPayloadFromJwt(splitJwtToken, "user", UserResponse.class);
//                        msg.append(", client=").append(userResponse.getUsername());
//                        msg.append(", payload=").append(payload);
//                    } else {
                JsonElement jsonElement = JsonParser.parseString(payload);
                String parsed = gson.toJson(jsonElement);
                msg.append("[ payload ]\n").append(parsed);
//                    }
//                } catch (JsonSyntaxException e) {
//                    log.info("cannot parse payload : " + payload);
//                } catch (JwtValidationFailException e) {
//                    log.warn("jwt validation failed");
//                }
            }
        }

        msg.append(suffix);
        return msg.toString();
    }
}
