package org.hw_sorter.service.content;

import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.CharacterContentHistory;
import org.hw_sorter.hw_rdbms.character.repo.CharacterContentHistoryRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ContentHistorySchedulerTest {
    @Autowired
    private ContentHistoryScheduler contentHistoryScheduler;
    @Autowired
    private CharacterContentHistoryRepo characterContentHistoryRepo;

    @Test
    @Transactional
    @Rollback
    void archiveContentHistory() {
        contentHistoryScheduler.archiveContentHistory();

        characterContentHistoryRepo.findAll().stream().map(CharacterContentHistory::toString).forEach(log::info);
    }
}