package org.hw_sorter.hw_rdbms.user.repo;

import org.hw_sorter.hw_rdbms.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
