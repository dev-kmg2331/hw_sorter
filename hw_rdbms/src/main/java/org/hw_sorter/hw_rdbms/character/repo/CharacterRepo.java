package org.hw_sorter.hw_rdbms.character.repo;

import org.hw_sorter.hw_rdbms.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepo extends JpaRepository<Character, Long> {
}
