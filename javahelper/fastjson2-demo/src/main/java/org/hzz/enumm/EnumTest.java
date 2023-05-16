package org.hzz.enumm;

import com.alibaba.fastjson2.JSON;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnumTest {

    @Test
    public void test_enum_writer(){
        String json = JSON.toJSONString(R.ok("hello"));
        System.out.println(json); // {"data":"hello","status":200}
    }

    @Test
    public void test_enum_reader(){
        String json = "{\"data\":\"hello\",\"status\":200}";
        R<String> r = JSON.parseObject(json,R.class);
        System.out.println(r); // R(data=hello, status=OK)
        assertEquals(r.getStatus(),HttpStatus.OK); // true
    }
}
