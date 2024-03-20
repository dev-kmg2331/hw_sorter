package org.hw_sorter.hw_api.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hw")
@Slf4j @RequiredArgsConstructor
public class ContentController {
    @GetMapping
    public ResponseEntity<?> getHwStatus() {
        return ResponseUtil.success();
    }
}
