package com.example.demo;

import org.apache.commons.lang.StringUtils;
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

    /**
     * redis sesion共享
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public String getUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username2");
        System.out.println("getUser:"+username);
        return username;
    }

    @GetMapping("/setUser")
    public String setUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("username2", "testSessionRedis|" + System.currentTimeMillis());
        System.out.println("shezhi:"+session.getAttribute("username2"));
        return "chenggong";
    }

    @GetMapping("/getUser2")
     public String getUser2(HttpServletRequest request) {
                 HttpSession session = request.getSession();
                String username = (String) session.getAttribute("username3");
        System.out.println("获取出来的数据："+username);
                 if (StringUtils.isEmpty(username)) {
                        username = "testSessionRedis|" + System.currentTimeMillis();
                        session.setAttribute("username3", username);
                     }
                 System.out.println("访问端口：" + request.getServerPort());
                 return username;
    }

}
