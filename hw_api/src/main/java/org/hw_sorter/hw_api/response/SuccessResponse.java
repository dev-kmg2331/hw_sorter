package org.hw_sorter.hw_api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class SuccessResponse<T> extends CommonResponse<T> {
    String msg;
    @Builder
    public SuccessResponse(T data, String msg) {
        super.setCode(200);
        super.setSuccess(true);
        super.setData(data);
        this.msg = msg;
    }
}
