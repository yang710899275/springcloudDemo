package com.example.demo.client;

import org.springframework.stereotype.Component;

@Component
public class cccc implements  UserFeignClient {
    @Override
    public String listUsers() {
        return "aaaaaaaaa";
    }
}
