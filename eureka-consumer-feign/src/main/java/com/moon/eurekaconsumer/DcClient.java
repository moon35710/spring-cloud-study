package com.moon.eurekaconsumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

    @GetMapping("/save")
    Object save();
}