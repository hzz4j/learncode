package org.hzz.entity;

import lombok.Data;
import lombok.Getter;

@Data
public class Result<T>{
    T data;
    Status status;
    public Result(T t,Status status){
        this.data = t;
        this.status = status;
    }
}
