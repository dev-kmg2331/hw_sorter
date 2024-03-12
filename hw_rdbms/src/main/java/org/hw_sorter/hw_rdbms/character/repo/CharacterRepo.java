package org.hw_sorter.hw_rdbms.character.repo;

import org.hw_sorter.hw_rdbms.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterRepo extends JpaRepository<Character, Long> {

    @Query("select c from character_la c where c.itemLevel >= :iLv and not exists(select 1 from CharacterContent ct where ct.character.id = c.id and ct.content.id = :contentId)")
    List<Character> findAllAvailableForContent(@Param("iLv") int iLevel, @Param("contentId") long contentId);
}
