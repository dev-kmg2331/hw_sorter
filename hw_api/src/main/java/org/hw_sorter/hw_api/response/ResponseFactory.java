package org.hw_sorter.hw_api.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseFactory {

    public <T> ResponseEntity<?> success(T data) {
        return ResponseEntity.ok(
                SuccessResponse.builder()
                        .data(data)
                        .build()
        );
    }

    public <T> ResponseEntity<?> fail(T data, int status, String errMsg) {
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
