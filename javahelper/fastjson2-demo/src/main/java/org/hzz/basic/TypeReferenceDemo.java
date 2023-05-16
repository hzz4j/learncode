package org.hzz.basic;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.hzz.entity.Result;
import org.hzz.entity.Status;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class TypeReferenceDemo {
    @Test
    public void single_test(){
        Result<int[]> result = new Result(new int[]{3,20}, Status.OK);
        String json = JSON.toJSONString(result);
        System.out.println(json); // {"data":[3,20],"status":"OK"}
        // 反序列化
        final Type type = new TypeReference<Result<int[]>>(){}.getType();
        Result<int[]> result1 = JSON.parseObject(json, new TypeReference<Result<int[]>>() {
        });
        result1.equals(result);
        assertEquals(result, result1); // 成功
        System.out.println(result1);
    }
}
