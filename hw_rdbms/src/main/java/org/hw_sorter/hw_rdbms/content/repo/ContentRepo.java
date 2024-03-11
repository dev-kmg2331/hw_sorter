package org.hw_sorter.hw_rdbms.content.repo;

import org.hw_sorter.hw_rdbms.content.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepo extends JpaRepository<Content, Long> {
}
