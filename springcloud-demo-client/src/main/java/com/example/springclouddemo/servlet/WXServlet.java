package com.example.springclouddemo.servlet;

import com.example.springclouddemo.util.Message;
import com.example.springclouddemo.util.MessageUtil;
import com.example.springclouddemo.util.TestMessage;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@RestController
public class WXServlet {

    @GetMapping(value = "/wechat")
    @ResponseBody
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str = WxUtil.validate(request);
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    @PostMapping(value = "/wechat")
    @ResponseBody
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();



        try {

            Map<String , String> map = MessageUtil.xmlToMap(request);

            String ToUserName = map.get("ToUserName");

            System.out.println("开发者 微信号"+ToUserName);

            String FromUserName = map.get("FromUserName");

            System.out.println("发送方"+FromUserName);

            String CreateTime = map.get("CreateTime");

            System.out.println("消息创建时间"+CreateTime);

            String MsgType = map.get("MsgType");

            System.out.println("类型为:"+MsgType);

            String Content = map.get("Content");

            System.out.println("内容为:"+Content);

            String MsgId  = map.get("MsgId ");

            System.out.println("MsgId"+MsgId);

            String message = null;

            if (MsgType.equals(MessageUtil.MESSAGE_TEXT)) {//判断是否为文本消息类型

                if (Content.equals("1")) {

                    message = MessageUtil.initText(ToUserName, FromUserName,

                            "对啊！我也是这么觉得！帅哭了！");

                } else if(Content.equals("2")){

                    message = MessageUtil.initText(ToUserName, FromUserName,

                            "好可怜啊！你年级轻轻地就瞎了！");

                } else if(Content.equals("3")){

                    message = MessageUtil.initNewsMessage(ToUserName, FromUserName

                            );

                } else if(Content.equals("?") || Content.equals("？")){

                    message = MessageUtil.initText(ToUserName, FromUserName,

                            MessageUtil.menuText());

                } else {

                    message = MessageUtil.initText(ToUserName, FromUserName,

                            "没让你选的就别瞎嘚瑟！！！");

                }



            }else if(MsgType.equals(MessageUtil.MESSAGE_EVENT)){//判断是否为事件类型

//从集合中，或许是哪一种事件传入

                String eventType = map.get("Event");

//关注事件

                if (eventType.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {

                    message = MessageUtil.initText(ToUserName, FromUserName,

                            MessageUtil.menuText());

                }

            }



            System.out.println("aaaa"+message);

            out.print(message);



        } catch (DocumentException e) {

            e.printStackTrace();

        }finally{

            out.close();
        }
    }

}
