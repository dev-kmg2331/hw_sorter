package org.hw_sorter.hw_rdbms.character.repo;

import org.hw_sorter.hw_rdbms.character.CharacterContentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterContentHistoryRepo extends JpaRepository<CharacterContentHistory, Long> {
}
