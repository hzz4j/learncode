package org.hzz.entity;

public class RespResult<T>{
    private Integer code;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RespResult<T> success(T data){
        RespResult<T> respResult = new RespResult<>();
        respResult.setCode(200);
        respResult.setData(data);
        return respResult;
    }
}
