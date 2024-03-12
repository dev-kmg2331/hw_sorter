package org.hw_sorter.service.content;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.CharacterContent;
import org.hw_sorter.hw_rdbms.character.CharacterContentHistory;
import org.hw_sorter.hw_rdbms.character.repo.CharacterContentHistoryRepo;
import org.hw_sorter.hw_rdbms.character.repo.CharacterContentRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor @Slf4j
public class ContentHistoryScheduler {
    private final CharacterContentRepo characterContentRepo;
    private final CharacterContentHistoryRepo characterContentHistoryRepo;
    @Scheduled(cron = "0 0 6 * * 3")
    @Transactional
    public void archiveContentHistory() {
        characterContentHistoryRepo.saveAll(
                characterContentRepo.findAll()
                        .stream()
                        .map(this::toHistory)
                        .toList()
        );

        characterContentRepo.deleteAll();
    }

    private CharacterContentHistory toHistory(CharacterContent characterContent) {
        return new CharacterContentHistory(characterContent.getCharacter(), characterContent.getContent());
    }
}
