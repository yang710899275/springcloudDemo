package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-user" ,fallback = cccc.class)
public interface UserFeignClient {

    @GetMapping(value = "/listUsers")
    public String listUsers();
}
