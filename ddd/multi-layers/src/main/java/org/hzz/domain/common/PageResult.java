package org.hzz.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageResult<T> {

    /**
     * 当前页
     */
    @Schema(description = "当前页",example = "1")
    private Integer pageNo;

    /**
     * 每页行数
     */
    @Schema(description = "每页行数",example = "10")
    private Integer pageSize;

    /**
     * 总记录
     */
    @Schema(description = "总记录",example = "100")
    private Long total;

    /**
     * 总页数
     */
    @Schema(description = "总页数",example = "10")
    private Long pageNum;

    /**
     * 内容
     */
    @Schema(description = "内容")
    private T data;

}
