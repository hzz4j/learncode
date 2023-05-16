package org.hzz.basic;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.hzz.entity.MultiResult;
import org.hzz.entity.Result;
import org.hzz.entity.Status;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TypeReferenceDemo {
    @Test
    public void single_test(){
        Result<List<Integer>> result = new Result(Arrays.asList(3,20), Status.OK);
        String json = JSON.toJSONString(result);
        // 反序列化
        Result<List<Integer>> result1 = JSON.parseObject(json, new TypeReference<Result<List<Integer>>>() {
        });
        assertEquals(result, result1); // 成功
    }


    private static final Type type = new TypeReference<Result<List<Integer>>>(){}.getType();
    @Test
    public void single_test2(){
        Result<List<Integer>> result = new Result(Arrays.asList(3,20), Status.OK);
        String json = JSON.toJSONString(result);
        // 反序列化
        Result<List<Integer>> result1 = JSON.parseObject(json, type);
        assertEquals(result, result1); // 成功
    }


    private static final Type type2 = new TypeReference<MultiResult<List<Integer>,List<String>>>() {}.getType();
    @Test
    public void multi_test(){
        MultiResult<List<Integer>,List<String>> multiResult = new MultiResult(Arrays.asList(3,20),
                Arrays.asList("hzz","Q10Viking"));
        String json = JSON.toJSONString(multiResult);
        System.out.println(json); // {"data1":[3,20],"data2":["hzz","Q10Viking"]}
        // 反序列化
        MultiResult<List<Integer>,List<String>> multiResult1 = JSON.parseObject(json,type2);
        assertEquals(multiResult, multiResult1); // 成功
    }
}
