package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping(value = "/listUsers")
    public String listUsers();
}
