package org.hw_sorter.service.content;

import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.character.repo.CharacterRepo;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.content.repo.ContentRepo;
import org.hw_sorter.hw_rdbms.user.User;
import org.hw_sorter.hw_rdbms.user.repo.UserRepo;
import org.hw_sorter.service.dto.ContentSortResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ContentSortServiceTest {

    @Autowired
    private ContentSortService contentSortService;
    @Autowired
    private CharacterRepo characterRepo;
    @Autowired
    private ContentRepo contentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContentCache contentCache;

    private long contentId;

    @Test
    void save() {
        contentId = contentRepo.save(
                Content.builder()
                        .difficulty(Content.Difficulty.HARD)
                        .gold("21000")
                        .name("kamen")
                        .requiredPersonNum(8)
                        .itemLevel("1630")
                        .build()
        ).getId();

        User user = userRepo.save(
                User.builder()
                        .username("test123")
                        .password("1234")
                        .build()
        );

        characterRepo.saveAll(List.of(
                        Character.builder()
                                .name("test_class_1")
                                .description("test_description")
                                .goldGenerate(true)
                                .itemLevel("1645")
                                .user(user)
                                .role(Character.ClassRole.DPS)
                                .build(),
                        Character.builder()
                                .name("test_class_2")
                                .description("test_description")
                                .goldGenerate(true)
                                .itemLevel("1641")
                                .user(user)
                                .role(Character.ClassRole.DPS)
                                .build(),
                        Character.builder()
                                .name("test_class_3")
                                .description("test_description")
                                .goldGenerate(true)
                                .itemLevel("1641")
                                .user(user)
                                .role(Character.ClassRole.SUP)
                                .build(),
                        Character.builder()
                                .name("test_class_4")
                                .description("test_description")
                                .goldGenerate(true)
                                .itemLevel("1630")
                                .user(user)
                                .role(Character.ClassRole.SUP)
                                .build(),
                        Character.builder()
                                .name("test_class_5")
                                .description("test_description")
                                .goldGenerate(true)
                                .itemLevel("1620")
                                .user(user)
                                .role(Character.ClassRole.SUP)
                                .build()
                )
        );

    }

    @Test
    void searchAvailableCharactersForContent() {
        contentCache.init(contentRepo.findAll());
        ContentSortResponse contentSortResponse = contentSortService.searchAvailableCharactersForContent(1L);
        log.info(contentSortResponse.toString());
    }

}