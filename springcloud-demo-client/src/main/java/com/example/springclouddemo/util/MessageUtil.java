package com.example.springclouddemo.util;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";

    public static final String MESSAGE_IMAGE = "image";

    public static final String MESSAGE_VOICE = "voice";

    public static final String MESSAGE_VIDEO = "video";

    public static final String MESSAGE_SHORTVIDEO = "shortvideo";

    public static final String MESSAGE_LINK = "link";

    public static final String MESSAGE_LOCATION = "location";

    public static final String MESSAGE_EVENT = "event";

    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_CLICK = "CLICK";

    public static final String MESSAGE_VIEW = "VIEW";

    public static final String MESSAGE_SCAN = "SCAN";

    /**

     * 将XML转为MAP集合

     * @param request

     * @return

     * @throws IOException

     * @throws DocumentException

     */

    public static Map<String , String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {

        Map<String , String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();

//从request对象中获取输入流

        InputStream ins = request.getInputStream();

//使用reader对象读取输入流,解析为XML文档

        Document doc = reader.read(ins);

//获取XML根元素

        Element root = doc.getRootElement();

//将根元素的所有节点，放入列表中

        List<Element> list = root.elements();

//遍历list对象，并保存到集合中

        for (Element element : list) {

            map.put(element.getName(), element.getText());

        }

        ins.close();

        return map;

    }

    /**

     * 将文本消息对象转成XML

     * @param text

     * @return

     */

    public static String textMessageToXml(TextMessage textMessage){

        XStream xstream = new XStream();

//将xml的根节点替换成<xml>  默认为TextMessage的包名

        xstream.alias("xml", textMessage.getClass());

        return xstream.toXML(textMessage);

    }

    public static String newsMessageToXml(NewsMessage newsMessage){

        XStream xstream = new XStream();

//将xml的根节点替换成<xml>  默认为TextMessage的包名

        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);

    }
    /**

     * 拼接关注主菜单

     */

    public static String menuText(){

        StringBuffer sb = new StringBuffer();

        sb.append("欢迎关注史上最帅公众号，请选择:\n\n");

        sb.append("1、真帅。\n");

        sb.append("2、并不帅。\n\n");

        sb.append("3、图文test。\n\n");

        sb.append("回复？调出主菜单。\n\n");

        return sb.toString();

    }

    /**

     * 初始化回复消息

     */

    public static String initText(String toUSerName,String fromUserName,String content){

        TextMessage text = new TextMessage();

        text.setFromUserName(toUSerName);

        text.setToUserName(fromUserName);

        text.setMsgType(MESSAGE_TEXT);

        text.setCreateTime(new Date().getTime());

        text.setContent(content);

        return MessageUtil.textMessageToXml(text);

    }

    /**
     * 初始化图文消息
     */
    public static String initNewsMessage(String toUSerName,String fromUserName){
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();
        //组建一条图文↓ ↓ ↓
        News newsItem = new News();
        newsItem.setTitle("欢迎来到杰瑞教育");
        newsItem.setDescription("杰瑞教育，中国高端互联网人才培训领导品牌！");
        newsItem.setPicUrl("http://www.jredu100.com/statics/images/index/top/logo.png");
        newsItem.setUrl("www.jredu100.com");
        newsList.add(newsItem);

        //组装图文消息相关信息
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUSerName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType("news");
        newsMessage.setArticle(newsList);
        newsMessage.setArticleCount(newsList.size());

        //调用newsMessageToXml将图文消息转化为XML结构并返回
        return MessageUtil.newsMessageToXml(newsMessage);
    }
}

