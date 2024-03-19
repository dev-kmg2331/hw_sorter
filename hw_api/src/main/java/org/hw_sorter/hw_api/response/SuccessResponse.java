package org.hw_sorter.hw_api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class SuccessResponse<T> extends CommonResponse<T> {
    @Builder
    public SuccessResponse(T data) {
        super.setCode(200);
        super.setSuccess(true);
        super.setData(data);
    }
}
