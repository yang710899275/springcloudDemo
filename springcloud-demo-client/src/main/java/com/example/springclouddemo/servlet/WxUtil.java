package com.example.springclouddemo.servlet;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Arrays;

public class WxUtil {

    /*验证服务器地址*/
    public static String validate(HttpServletRequest request) {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String token = "abc123456";

        String str = "";

        try {

            if (null != signature) {
                String[] ArrTmp = {token, timestamp, nonce};
                Arrays.sort(ArrTmp);
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < ArrTmp.length; i++) {
                    sb.append(ArrTmp[i]);
                }

                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] bytes = md.digest(new String(sb).getBytes());
                StringBuffer buf = new StringBuffer();
                for (int i = 0; i < bytes.length; i++) {
                    if (((int) bytes[i] & 0xff) < 0x10) {
                        buf.append("0");
                    }
                    buf.append(Long.toString((int) bytes[i] & 0xff, 16));
                }
                if (signature.equals(buf.toString())) {
                    str = echostr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    /*从request获得报文*/
    public static String getStringInputStream(HttpServletRequest request) {

        InputStreamReader reader = null;
        BufferedReader breader = null;
        StringBuffer strb = new StringBuffer();

        try {
            reader = new InputStreamReader(request.getInputStream(), "UTF-8");
            breader = new BufferedReader(reader);
            String str = null;
            while (null != (str = breader.readLine())) {
                strb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (null != breader) {
            try {
                breader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strb.toString();
    }

    /*读取tag信息*/
    public static String getXMLCDATA(String requestStr, String tagName) {
        String beginTagName = "<" + tagName + "><![CDATA[";
        String endTagName = "]]></" + tagName + ">";
        String str = null;
        boolean flagIsLong = false;

        if ("CreateTime".equals(tagName)) {
            flagIsLong = true;
        } else if ("MsgId".equals(tagName)) {
            flagIsLong = true;
        } else if ("MsgID".equals(tagName)) {
            flagIsLong = true;
        } else if ("Location_X".equals(tagName)) {
            flagIsLong = true;
        } else if ("Location_Y".equals(tagName)) {
            flagIsLong = true;
        } else if ("Scale".equals(tagName)) {
            flagIsLong = true;
        }

        if (flagIsLong) {
            beginTagName = "<" + tagName + ">";
            endTagName = "</" + tagName + ">";
        }

        int beginIndex = requestStr.indexOf(beginTagName) + beginTagName.length();
        int endIndex = requestStr.indexOf(endTagName);

        try {
            str = requestStr.substring(beginIndex, endIndex);
        } catch (Exception e) {
            str = null;
        }
        return str;
    }

}
