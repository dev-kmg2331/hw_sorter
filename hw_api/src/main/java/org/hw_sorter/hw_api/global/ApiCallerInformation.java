package org.hw_sorter.hw_api.global;

import lombok.Getter;
import lombok.ToString;
import org.hw_sorter.service.user.dto.UserResponse;

@Getter @ToString
public class ApiCallerInformation {
    private final UserResponse userResponse;

    public ApiCallerInformation(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
