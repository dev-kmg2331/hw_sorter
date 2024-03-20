package org.hw_sorter.hw_api.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseUtil {

    public static ResponseEntity<?> success() {
        return success(null);
    }

    public static <T> ResponseEntity<?> success(T data) {
        return ResponseEntity.ok(
                SuccessResponse.builder()
                        .data(data)
                        .msg("request success")
                        .build()
        );
    }

    public static <T> ResponseEntity<?> fail(int status, String errMsg) {
        return fail(status, errMsg, null);
    }

    public static <T> ResponseEntity<?> fail(int status, String errMsg, T data) {
        return ResponseEntity.status(status)
                .body(
                        FailResponse.builder()
                                .data(data)
                                .status(status)
                                .errorMsg(errMsg)
                                .build()
                );
    }
}
