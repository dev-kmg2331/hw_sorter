package org.hw_sorter.hw_rdbms.character.repo;

import org.hw_sorter.hw_rdbms.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CharacterRepo extends JpaRepository<Character, Long> {

    @Query("select c from character_la c where c.itemLevel >= :iLv")
    List<Character> findAllAvailableCharsForContent(@Param("iLv") int iLevel/*, @Param("contentId") long contentId*/);


    Optional<Character> findByName(String name);
}
