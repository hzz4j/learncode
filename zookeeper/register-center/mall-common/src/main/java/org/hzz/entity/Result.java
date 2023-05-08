package org.hzz.entity;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {
    public Result() {
        put("code", 0);
        put("msg", "success");
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
