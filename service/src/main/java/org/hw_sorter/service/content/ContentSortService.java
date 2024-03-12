package org.hw_sorter.service.content;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.character.Character;
import org.hw_sorter.hw_rdbms.character.repo.CharacterRepo;
import org.hw_sorter.hw_rdbms.content.Content;
import org.hw_sorter.hw_rdbms.content.repo.ContentRepo;
import org.hw_sorter.service.character.dto.CharacterData;
import org.hw_sorter.service.dto.ContentSortResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentSortService {
    private final CharacterRepo characterRepo;
    private final ContentRepo contentRepo;
    private final ContentCache contentCache;

    @PostConstruct
    public void init() {
        contentCache.init(contentRepo.findAll());
    }

    @Transactional(readOnly = true)
    public ContentSortResponse searchAvailableCharactersForContent(Long contentId) {
        Content content = contentCache.getContent(contentId);
        String itemLevel = content.getItemLevel();

        Map<Boolean, List<Character>> roleMap = characterRepo.findAllAvailableForContent(Integer.parseInt(itemLevel), content.getId())
                .stream()
                .collect(
                        Collectors.partitioningBy(
                                character -> character.getRole().equals(Character.ClassRole.DPS)
                        )
                );

        List<Character> dps = roleMap.get(true);
        List<Character> sup = roleMap.get(false);

        return ContentSortResponse.builder()
                .availDps(dps.stream().map(CharacterData::from).collect(Collectors.toList()))
                .availSup(sup.stream().map(CharacterData::from).collect(Collectors.toList()))
                .build();
    }
}
