package org.hw_sorter.hw_api.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CommonResponse<T> {
    private boolean success;
    private int code;

    private T data;
}
