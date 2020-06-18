package com.example.springclouddemo.util;

import javax.xml.soap.Text;

public class TextMessage extends  TextMessageBase{



    private String Content;

    private String MsgId;





    public String getContent() {

        return Content;

    }

    public void setContent(String content) {

        Content = content;

    }

    public String getMsgId() {

        return MsgId;

    }

    public void setMsgId(String msgId) {

        MsgId = msgId;

    }

    public TextMessage(String toUserName, String fromUserName,

                        Long createTime, String msgType, String content, String msgId) {

        super();

        ToUserName = toUserName;

        FromUserName = fromUserName;

        CreateTime = createTime;

        MsgType = msgType;

        Content = content;

        MsgId = msgId;

    }

    public TextMessage() {

        super();

    }

}

