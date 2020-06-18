package springclouddemodocker.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {
    protected static Logger logger = LoggerFactory.getLogger(testController.class);

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        System.out.println("aaa");
        logger.info("test1111111");
        return "ssssss";
    }
}
