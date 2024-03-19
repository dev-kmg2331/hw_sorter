package org.hw_sorter.service.user.dto;

import lombok.*;
import org.hw_sorter.hw_rdbms.user.User;

@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String username;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .build();
    }
}
