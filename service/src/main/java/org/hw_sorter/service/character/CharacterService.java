package org.hw_sorter.service.character;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_la_client.LaClient;
import org.hw_sorter.service.character.dto.CharacterAdd;
import org.springframework.stereotype.Service;

@Service
@Slf4j @RequiredArgsConstructor
public class CharacterService {
    private final LaClient laClient;
    public void register(CharacterAdd param) {
    }

    public String search(String name) {
        return laClient.searchCharacter(name);
    }
}
