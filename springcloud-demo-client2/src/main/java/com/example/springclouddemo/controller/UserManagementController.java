package com.example.springclouddemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class UserManagementController {

    @Value("${server.port}")
    String serverPort;

    @GetMapping("/listUsers")
    public String ListUsers(){
        List<Map<String,Object>> users= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String,Object> m=new HashMap<>();
            m.put("id",i);
            m.put("name","aa"+i);
            users.add(m);

        }
        return "服务器端口"+serverPort + ",信息为:"+users;
    }

    /**
     * redis sesion共享
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public String getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
//        session.setAttribute("username1", "testSessionRedis|" + System.currentTimeMillis());
        String username = (String)session.getAttribute("username1");
        System.out.println("aaaaaaaaaaaa:"+username);
        if(username == null){
            System.out.println("kong");
            session.setAttribute("username1", "testSessionRedis|" + System.currentTimeMillis());
        }
        return username;
    }

}
