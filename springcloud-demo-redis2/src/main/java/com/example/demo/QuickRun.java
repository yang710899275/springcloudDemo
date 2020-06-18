package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/v1")
public class QuickRun {
    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public String firstResp (HttpServletRequest request){

                request.getSession().setAttribute("reques", "abcccccccccccc");

                return "sssss";
            }

            @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){


                System.out.println(request.getSession().getAttribute("reques"));
                return request.getSession().getAttribute("reques");
            }
}
