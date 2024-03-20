package org.hw_sorter.service.character.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hw_sorter.hw_rdbms.character.Character;

@Getter @AllArgsConstructor
public class CharacterBaseData {
    private long id;
    private String name;
    private String classname;
    private int itemLevel;
    private String description;
    private Character.ClassRole role;
}
