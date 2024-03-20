package org.hw_sorter.hw_api.endpoint.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.response.ResponseUtil;
import org.hw_sorter.service.character.exception.CharacterDuplicateException;
import org.hw_sorter.service.global.exception.InvalidAccessException;
import org.hw_sorter.service.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * @since version 1.0.0.2
     * character name duplicate exception handler
     * */
    @ExceptionHandler(CharacterDuplicateException.class)
    @ResponseBody
    public ResponseEntity<?> characterNameDuplicate(CharacterDuplicateException e) {
        log.warn("character name duplicate exception");
        return ResponseUtil.fail(400, "character name duplicate");
    }

    /**
     * @since version 1.0.0.2
     * user not found exception handler
     * */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> userNotFound(UserNotFoundException e) {
        log.warn("user not found exception");
        return ResponseUtil.fail(500, "character name duplicate");
    }

    /**
     * @since version 1.0.0.2
     * invalid access exception handler
     * */
    @ExceptionHandler(InvalidAccessException.class)
    @ResponseBody
    public ResponseEntity<?> invalidAccess(InvalidAccessException e) {
        log.warn("invalid access exception");
        return ResponseUtil.fail(400, "invalid access");
    }
}
