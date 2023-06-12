package com.livegoods.search.feign;

import com.livegoods.pojo.Houses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 基于OpenFeign实现远程服务调用。
 */
@FeignClient("livegoods-details")
public interface LivegoodsDetailsService {
    @GetMapping("/getAll")
    List<Houses> getAll();
}
