package org.hzz.domain.common;

import java.util.LinkedHashMap;

public class Result<T> extends LinkedHashMap<String, Object> {

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.put("code", "200");
        result.put("msg", "success");
        result.put("data", data);
        return result;
    }

    public static <T> Result<T> error(T data, String code, String msg){
        Result<T> result = new Result<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("error", data);
        return result;
    }
}
