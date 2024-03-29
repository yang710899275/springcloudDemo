package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserManagermentRibbonClient {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/listUsersByRibbon")
    public String ListUsersByRibbon(){
        String result = this.restTemplate.getForObject("http://service-user/listUsers",String.class);
        return result;
    }
}
