package org.hzz.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery<T> {
    @Schema(description = "当前页(默认为1)",example = "1")
    private Integer pageNo = 1;

    @Schema(description = "每页行数(默认为20)",example = "20")
    private Integer pageSize = 20;

    /**
     * 动态查询
     */
    @Schema(description = "动态查询")
    private T query;
}
