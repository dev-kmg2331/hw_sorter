package org.hw_sorter.hw_api.response;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class FailResponse<T> extends CommonResponse<T> {
    private final String errorMsg;

    @Builder
    public FailResponse(T data, int status, String errorMsg) {
        super.setCode(status);
        super.setSuccess(true);
        super.setData(data);
        this.errorMsg = errorMsg;
    }
}
