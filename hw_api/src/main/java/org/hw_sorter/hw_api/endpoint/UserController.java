package org.hw_sorter.hw_api.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.config.jwt.JwtConfig;
import org.hw_sorter.hw_api.response.ResponseUtil;
import org.hw_sorter.service.user.UserService;
import org.hw_sorter.service.user.dto.UserLogin;
import org.hw_sorter.service.user.dto.UserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j @RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("login")
    @Operation(summary = "유저 로그인")
    public ResponseEntity<?> userLogin(@RequestBody UserLogin userLogin, HttpServletResponse response) {
        UserResponse userResponse = userService.login(userLogin);

        response.setHeader(
                HttpHeaders.AUTHORIZATION,
                "Bearer " + JwtConfig.createJwt(JwtConfig.JwtType.ACCESS, "user", userResponse)
        );

        return ResponseUtil.success(userResponse);
    }
}
