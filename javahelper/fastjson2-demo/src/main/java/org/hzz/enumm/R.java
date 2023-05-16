package org.hzz.enumm;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class R<T>  {
    T data;

    @JSONField(serializeUsing = StatusEnumWriter.class,
               deserializeUsing = StatusEnumReader.class)
    HttpStatus status;

    public static <T> R<T> ok(T t){
        return new R<T>(t,HttpStatus.OK);
    }
}
