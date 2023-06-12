package com.livegoods.message.consumer.order.openfeign;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Houses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("livegoods-details")
public interface DetailsServiceInterface {
    @GetMapping("/details")
    Houses showHouseDetailsById(@RequestParam("id") String id);
    @PostMapping("/modifyHousesNum")
    Result<Houses> modifyHousesNum(@RequestBody Houses houses);
}
