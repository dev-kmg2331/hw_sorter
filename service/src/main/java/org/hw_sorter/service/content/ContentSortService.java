package org.hw_sorter.service.content;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.repo.CharacterRepo;
import org.hw_sorter.hw_rdbms.content.repo.ContentRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class ContentSortService {
    private final CharacterRepo characterRepo;
    private final ContentRepo contentRepo;
    private final ContentCache contentCache;

    @PostConstruct
    public void cacheContent() {
        log.info("============initializing content cache============");
        contentCache.init(contentRepo.findAll());
    }

    @Transactional(readOnly = true)
    public void searchAvailableCharactersForContent(Long contentId) {
        contentCache.getContent(contentId);
    }
}
