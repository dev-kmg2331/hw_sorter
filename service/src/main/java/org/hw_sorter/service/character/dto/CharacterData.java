package org.hw_sorter.service.character.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hw_sorter.hw_rdbms.character.Character;

@Getter @Builder @ToString
public class CharacterData {
    private String name;
    private int itemLevel;
    private int contentCount;
    private Character.ClassRole role;

    public static CharacterData from(Character entity) {
        return CharacterData.builder()
                .name(entity.getName())
                .itemLevel(entity.getItemLevel())
                .role(entity.getRole())
                .build();
    }
}
