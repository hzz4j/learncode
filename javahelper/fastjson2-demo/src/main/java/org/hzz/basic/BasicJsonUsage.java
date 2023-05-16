package org.hzz.basic;

import com.alibaba.fastjson2.JSON;
import org.hzz.entity.Book;
import org.hzz.entity.Entity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BasicJsonUsage {

    @Test
    public void test_object(){
        Book book = new Book("Thinking in Java", "Bruce Eckel");
        String json = JSON.toJSONString(book); // {"author":"Bruce Eckel","name":"Thinking in Java"}
        Book book1 = JSON.parseObject(json, Book.class);
        // assertSame(book, book1); // 失败，报错
        assertEquals(book, book1); // 成功
    }
    @Test
    public void test_list_tojson(){
        List<Entity> entities = new ArrayList<Entity>();
        entities.add(new Entity(1,"Q10Viking"));
        entities.add(new Entity("hzz"));
        System.out.println(JSON.toJSONString(entities)); // [{"id":1,"name":"Q10Viking"},{"name":"hzz"}]
    }

    @Test
    public void test_json_tolist(){
        String json = "[{\"id\":1,\"name\":\"Q10Viking\"},{\"name\":\"hzz\"}]";
        List<Entity> entities = JSON.parseArray(json, Entity.class);
        // [Entity(id=1, name=Q10Viking, value=null), Entity(id=null, name=hzz, value=null)]
        System.out.println(Arrays.toString(entities.toArray()));
    }
}
