package org.hw_sorter.service.character;

import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.user.User;
import org.hw_sorter.hw_rdbms.user.repo.UserRepo;
import org.hw_sorter.service.character.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles({""})
@Slf4j
class CharacterServiceTest {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private UserRepo userRepo;

    @Test
    void register() {
        User user = userRepo.save(
                User.builder()
                        .username("123test")
                        .password("1234")
                        .build()
        );

        List.of(
                Character.builder()
                        .name("test_class_1")
                        .description("test_description")
                        .goldGenerate(true)
                        .itemLevel(1645)
                        .user(user)
                        .role(Character.ClassRole.DPS)
                        .build(),
                Character.builder()
                        .name("test_class_2")
                        .description("test_description")
                        .goldGenerate(true)
                        .itemLevel(1641)
                        .user(user)
                        .role(Character.ClassRole.DPS)
                        .build(),
                Character.builder()
                        .name("test_class_3")
                        .description("test_description")
                        .goldGenerate(true)
                        .itemLevel(1641)
                        .user(user)
                        .role(Character.ClassRole.SUP)
                        .build()
        );
    }

    @Test
    void search() {
    }

    @Test
    @Transactional
    void checkContentClear() {
        characterService.checkContent(10, 1, false);

        Assertions.assertThrows(EntityNotFoundException.class, () -> characterService.checkContent(1, 2, false));

        Map<Content, Boolean> contentCheck =
                characterService.searchByName("test_class_6").getContentCheck();

        log.info(contentCheck.toString());
    }
}