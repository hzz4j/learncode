package com.tuling.map;

import com.tuling.entity.User;
import org.apache.ibatis.cache.decorators.TransactionalCache;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class TestComputeIfAbsent {

    @Test
    public void test01(){
        Map<String,Object> map=new HashMap<>();
        // java8之前。从map中根据key获取value操作可能会有下面的操作
        Object key = map.get("key");
        if (key == null) {
            key = new Object();
            map.put("key", key);
        }

        // java8之后。上面的操作可以简化为一行，若key对应的value为空，会将第二个参数的返回值存入并返回
        Object key2 = map.computeIfAbsent("key", k -> new Object());

        Object key3 = map.computeIfAbsent("key", MyObject::new);
    }

    public class MyObject {
        private String key;

        public MyObject(String key) {
            this.key = key;
        }
    }

}




