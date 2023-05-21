package org.hzz.domain.common;

import lombok.Data;


@Data
public class PageQuery<T> {


    private Integer pageNum = 1;

    private Integer pageSize = 20;

    /**
     * 动态查询
     */
    private T query;
}
