package com.example.demo.api;

import com.example.demo.client.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignApi {
    @Autowired
    UserFeignClient userFeignClient;

    @GetMapping(value = "/listUsersByFeign")
    public String ListUsers(){
        String users = this.userFeignClient.listUsers();
        return users;
    }

}
