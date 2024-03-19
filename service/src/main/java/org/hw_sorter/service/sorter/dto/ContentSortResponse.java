package org.hw_sorter.service.sorter.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hw_sorter.service.character.dto.CharacterData;

import java.util.List;

@Getter @Builder
public class ContentSortResponse {
    private final List<CharacterData> availDps;
    private final List<CharacterData> availSup;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(this);
    }
}
