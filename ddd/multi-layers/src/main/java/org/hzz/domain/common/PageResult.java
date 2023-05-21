package org.hzz.domain.common;

import lombok.Data;

@Data
public class PageResult<T> {

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 每页行数
     */
    private Integer pageSize;

    /**
     * 总记录
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pageNum;

    /**
     * 内容
     */
    private T data;

}
