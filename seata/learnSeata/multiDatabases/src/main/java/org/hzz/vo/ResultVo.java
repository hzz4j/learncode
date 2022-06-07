package org.hzz.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultVo extends HashMap<String, Object> {

    public ResultVo() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResultVo error(int code, String msg) {
        ResultVo r = new ResultVo();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultVo ok(String msg) {
        ResultVo r = new ResultVo();
        r.put("msg", msg);
        return r;
    }

    public static ResultVo ok(Map<String, Object> map) {
        ResultVo r = new ResultVo();
        r.putAll(map);
        return r;
    }

    public static ResultVo ok() {
        return new ResultVo();
    }

    @Override
    public ResultVo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

