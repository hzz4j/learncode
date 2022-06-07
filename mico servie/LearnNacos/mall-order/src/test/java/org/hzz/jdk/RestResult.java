package org.hzz.jdk;

import java.util.List;
import java.util.Map;

public class RestResult<T> {
    private int code;

    private String message;

    private T data;

    private Map<String, List<String>> headers;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", headers=" + headers +
                '}';
    }

    public static <T>  RestResultBuilder<T> builder(){ return new RestResultBuilder<T>();}

    public static final class RestResultBuilder<T>{
        private int code;
        private String message;
        private T data;
        private Map<String, List<String>> headers;

        public RestResultBuilder<T> withCode(int code){
            this.code = code;
            return this;
        }

        public RestResultBuilder<T> withMsg(String msg){
            this.message = msg;
            return this;
        }

        public RestResultBuilder<T> withHeaders(Map<String, List<String>> headers){
            this.headers = headers;
            return this;
        }

        public RestResultBuilder<T> withData(T data){
            this.data = data;
            return this;
        }

        public RestResult<T> build(){
            RestResult<T> restResult = new RestResult<>();
            restResult.setCode(this.code);
            restResult.setData(this.data);
            restResult.setMessage(this.message);
            restResult.setHeaders(this.headers);
            return restResult;
        }

    }
}
