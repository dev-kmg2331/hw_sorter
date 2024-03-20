package org.hw_sorter.hw_api.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.global.annotation.ApiCaller;
import org.hw_sorter.hw_api.response.ResponseUtil;
import org.hw_sorter.service.character.CharacterService;
import org.hw_sorter.service.character.dto.CharacterBaseData;
import org.hw_sorter.service.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/character")
@Slf4j @RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    /**
     * @since version 1.0.0.2
     * */
    @PostMapping
    @Operation(summary = "add character")
    public ResponseEntity<?> register(
            @RequestBody CharacterBaseData baseData,
            @ApiCaller UserResponse user
    ) {
        characterService.register(baseData, user);

        return ResponseUtil.success();
    }

    /**
     * @since version 1.0.0.2
     * */
    @GetMapping("list")
    @Operation(summary = "user character list")
    public ResponseEntity<?> showCharacters(
            @ApiCaller UserResponse user
    ) {
        return ResponseUtil.success(characterService.showUserCharacters(user));
    }

    /**
     * @since version 1.0.0.2
     * */
    @PutMapping
    @Operation(summary = "update character")
    public ResponseEntity<?> update(
            @RequestBody CharacterBaseData baseData,
            @ApiCaller UserResponse user
    ) {
        characterService.update(baseData, user);

        return ResponseUtil.success();
    }

    /**
     * @since version 1.0.0.2
     * */
    @PostMapping
    @Operation(summary = "delete character")
    public ResponseEntity<?> remove(
            @RequestBody CharacterBaseData characterData,
            @ApiCaller UserResponse user
    ) {
        characterService.remove(characterData, user);

        return ResponseUtil.success();
    }
}
