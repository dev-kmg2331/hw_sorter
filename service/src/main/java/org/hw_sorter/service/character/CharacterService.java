package org.hw_sorter.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_la_client.ReactiveClient;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.character.repo.CharacterRepo;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.content.repo.ContentRepo;
import org.hw_sorter.hw_rdbms.user.User;
import org.hw_sorter.hw_rdbms.user.repo.UserRepo;
import org.hw_sorter.service.character.dto.CharacterBaseData;
import org.hw_sorter.service.character.dto.CharacterData;
import org.hw_sorter.service.character.exception.CharacterDuplicateException;
import org.hw_sorter.service.character.exception.EntityNotFoundException;
import org.hw_sorter.service.content.ContentCache;
import org.hw_sorter.service.global.exception.InvalidAccessException;
import org.hw_sorter.service.user.dto.UserResponse;
import org.hw_sorter.service.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterService {
    private final ReactiveClient reactiveClient;
    private final CharacterRepo characterRepo;
    private final ContentCache contentCache;
    private final ContentRepo contentRepo;
    private final UserRepo userRepo;

    /**
     * @since version 1.0.0.1
     * register new character
     * */
    @Transactional
    public void register(CharacterBaseData data, UserResponse user) {
        characterRepo.findByName(data.getName()).ifPresent(c -> {
            throw new CharacterDuplicateException();
        });

        User userEntity = userRepo.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);

        Character saved = characterRepo.save(
                Character.builder()
                        .name(data.getName())
                        .classname(data.getClassname())
                        .description(data.getDescription())
                        .goldGenerate(true)
                        .itemLevel(data.getItemLevel())
                        .user(userEntity)
                        .role(data.getRole())
                        .build()
        );

        // content set
        contentRepo.findAll().forEach(content -> saved.getContentCheck().put(content, false));
    }

    public List<CharacterData> showUserCharacters(UserResponse user) {
        User userEntity = userRepo.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);

        return userEntity.getCharacters().stream().map(CharacterData::from).collect(Collectors.toList());
    }

    /**
     * @since version 1.0.0.2
     * update character data
     * */
    @Transactional
    public void update(CharacterBaseData baseData, UserResponse user) {
        characterRepo.findById(baseData.getId())
                .ifPresentOrElse(
                        character -> {
                            // if character username does not match
                            if (!character.getUser().getUsername().equals(user.getUsername()))
                                throw new InvalidAccessException();

                            character.update(
                                    baseData.getName(),
                                    baseData.getClassname(),
                                    baseData.getItemLevel(),
                                    baseData.getDescription(),
                                    true,
                                    baseData.getRole()
                            );

                            log.info("character delete : {}", character);
                        }, () -> {
                            throw new EntityNotFoundException();
                        }
                );
    }

    /**
     * @since version 1.0.0.2
     * remove character data. cascade delete table content_check
     * @see Character
     * */
    public void remove(CharacterBaseData baseData, UserResponse user) {
        characterRepo.findById(baseData.getId())
                .ifPresentOrElse(
                        character -> {
                            // if character username does not match
                            if (!character.getUser().getUsername().equals(user.getUsername()))
                                throw new InvalidAccessException();

                            characterRepo.delete(character);

                            log.info("character delete : {}", character);
                        }, () -> {
                            throw new EntityNotFoundException();
                        }
                );
    }

    public Character searchByName(String name) {
        return characterRepo.findByName(name).orElseThrow(EntityNotFoundException::new);
    }


    /**
     * content clear check
     */
    @Transactional
    public void checkContent(long charId, long contentId, boolean check) {
        Character character = characterRepo.findById(charId).orElseThrow(EntityNotFoundException::new);
        Content content = contentCache.getContent(contentId);
        character.getContentCheck().computeIfPresent(content, (content1, aBoolean) -> check);
    }
}
