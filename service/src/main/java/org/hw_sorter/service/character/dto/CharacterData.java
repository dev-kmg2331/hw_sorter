package org.hw_sorter.service.character.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hw_sorter.hw_rdbms.character.Character;

@Getter @ToString
public class CharacterData extends CharacterBaseData {
    private int contentCount;

    @Builder
    public CharacterData(long id, String name, String classname, int itemLevel, String description, Character.ClassRole role, int contentCount) {
        super(id, name, classname, itemLevel, description, role);
        this.contentCount = contentCount;
    }

    public static CharacterData from(Character entity) {
        return CharacterData.builder()
                .name(entity.getName())
                .classname(entity.getClassname())
                .itemLevel(entity.getItemLevel())
                .role(entity.getRole())
                .description(entity.getDescription())
                .contentCount(entity.getContentCheck().size())
                .build();
    }

}