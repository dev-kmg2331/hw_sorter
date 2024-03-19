package org.hw_sorter.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_la_client.ReactiveClient;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.character.repo.CharacterRepo;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.content.repo.ContentRepo;
import org.hw_sorter.service.character.exception.CharacterDuplicateException;
import org.hw_sorter.service.character.exception.EntityNotFoundException;
import org.hw_sorter.service.content.ContentCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j @RequiredArgsConstructor
public class CharacterService {
    private final ReactiveClient reactiveClient;
    private final CharacterRepo characterRepo;
    private final ContentCache contentCache;
    private final ContentRepo contentRepo;

    /**
     * register new character
     * */
    @Transactional
    public void register(Character character) {
        characterRepo.findByName(character.getName()).ifPresent(c -> {
            throw new CharacterDuplicateException();
        });

        Character saved = characterRepo.save(character);

        // content set
        contentRepo.findAll().forEach(content -> saved.getContentCheck().put(content, false));
    }

    public Character searchByName(String name) {
        return characterRepo.findByName(name).orElseThrow(EntityNotFoundException::new);
    }



    /**
     * content clear check
     * */
    @Transactional
    public void checkContent(long charId, long contentId, boolean check) {
        Character character = characterRepo.findById(charId).orElseThrow(EntityNotFoundException::new);
        Content content = contentCache.getContent(contentId);
        character.getContentCheck().computeIfPresent(content, (content1, aBoolean) -> check);
    }
}
