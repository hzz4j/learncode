package org.hzz.entity;

import lombok.Data;

public enum Status {
    OK(200,"OK"),
    BAD_REQUEST(400,"Bad Request"),
    NOT_FOUND(404,"Not Found");
    private Integer code;
    private String desc;
    private Status(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Status fromCode(Integer code){
        for(Status status:Status.values()){
            if(status.code.equals(code)){
                return status;
            }
        }
        return null;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
}
