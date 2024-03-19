package org.hw_sorter.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_rdbms.user.User;
import org.hw_sorter.hw_rdbms.user.repo.UserRepo;
import org.hw_sorter.service.user.dto.UserLogin;
import org.hw_sorter.service.user.dto.UserResponse;
import org.hw_sorter.service.user.exception.InvalidLoginException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public UserResponse login(UserLogin userLogin) {
        User user = userRepo.findByUsername(userLogin.getUsername()).orElseThrow(InvalidLoginException::new);
        
        UserValidator.validateLogin(user, userLogin);

        return UserResponse.fromEntity(user);
    }

    public List<User> getUsers(List<UUID> ids) {
        return userRepo.findAllById(ids);
    }

    
    private class UserValidator {
        public static void validateLogin(User user, UserLogin userLogin) {
            // password unmatched
            if (!user.getPassword().equals(userLogin.getPassword()))
                throw new InvalidLoginException();


        }

    }
}
