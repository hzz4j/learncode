package org.hzz.enumm;


import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;

public class StatusEnumWriter implements ObjectWriter {
    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if(object == null) jsonWriter.writeNull();
        if(object instanceof Status){
            jsonWriter.writeInt32(((Status)object).getCode());
        }else{
            throw new RuntimeException("not support type"+object.getClass().getName());
        }
    }
}
