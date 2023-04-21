package org.hzz.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tcc-storage-service",path = "/storage")
public interface StorageFeignService {
    @RequestMapping(path = "/deduct")
    Boolean deduct(@RequestParam("commodityCode") String commodityCode,
                   @RequestParam("count") Integer count);
}
