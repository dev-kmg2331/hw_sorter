package org.hw_sorter.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.user.User;
import org.hw_sorter.hw_rdbms.user.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j @RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUser(UUID uuid) {
        userRepo.findById(uuid);
        return null;
    }

    public List<User> getUsers(List<UUID> ids) {
        return userRepo.findAllById(ids);
    }
}
