package com.tuling.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class TestMapKeyParam {
    public static final String PARAMETER_OBJECT_KEY = "_parameter";
    public static final String DATABASE_ID_KEY = "_databaseId";
    @Test
    public void test01(){
        Map<String,Object> map=new HashMap();
        map.put(PARAMETER_OBJECT_KEY,1);
        map.put(DATABASE_ID_KEY,null);

        System.out.println(map.get("_parameter"));
    }
}
