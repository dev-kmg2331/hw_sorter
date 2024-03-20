package org.hw_sorter.service.character.dto;

import lombok.Getter;
import lombok.ToString;
import org.hw_sorter.hw_rdbms.character.Character;

@Getter @ToString
public class CharacterRegisterParam {
    private String name;
    private int itemLevel;
    private int contentCount;
    private Character.ClassRole role;
}
