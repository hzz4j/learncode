package org.hzz.jsonpath;

import com.alibaba.fastjson2.JSONPath;
import org.hzz.entity.Entity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JsonPathDemo1 {

    @Test
    public void test_jsonpath(){
        Entity entity = new Entity(123, new Object());
        System.out.println(JSONPath.eval(entity,"$.id")); // 123
        assertSame(entity.getValue(), JSONPath.eval(entity, "$.value"));
        assertTrue(JSONPath.contains(entity, "$.value"));
        // fastjson版本2中没有containsValue方法和size方法
        // fastjson版本1中有containsValue方法和size方法

        //assertTrue(JSONPath.containsValue(entity, "$.id", 123));
        //assertTrue(JSONPath.containsValue(entity, "$.value", entity.getValue()));
        //assertEquals(2, JSONPath.size(entity, "$"));
        //assertEquals(0, JSONPath.size(new Object[], "$"));
    }

    @Test
    public void read_list_name(){
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity("Q10Viking"));
        entities.add(new Entity("hzz"));
        List<String> result = (List<String>)JSONPath.eval(entities, "$[*].name");
        System.out.println(Arrays.toString(result.toArray())); // [Q10Viking, hzz]
    }

    @Test
    public void readNameFromJsonList(){
        String json = "[{\"id\":1,\"name\":\"Q10Viking\"},{\"name\":\"hzz\"}]";
        List<String> list = (List<String>)JSONPath.eval(json, "$[*].name");
        System.out.println(Arrays.toString(list.toArray())); // [Q10Viking, hzz]
    }
}
