package org.hzz.feature;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Goods {
    @JSONField(ordinal = 1)
    private String name;

    // 不会转驼峰，优先级更高
    @JSONField(name = "authorAlias", ordinal = 2)
    private String author;

    @JSONField(serialize = false)
    private Double price;

    private List<Integer> subGoods;
}
