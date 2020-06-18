package com.example.springclouddemo.util;



import com.alibaba.fastjson.JSONObject;
import com.example.springclouddemo.entity.AccessToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WeiXinUtil {
    //从微信后台拿到APPID和APPSECRET 并封装为常量
    private static final String APPID = "wx9e15c570f4eae346";
    private static final String APPSECRET = "419014d5e49583e8208cc9ddaad50181";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    /**
     * 编写Get请求的方法。但没有参数传递的时候，可以使用Get请求
     *
     * @param url 需要请求的URL
     * @return 将请求URL后返回的数据，转为JSON格式，并return
     */



    /**
     * 获取AccessToken
     * @return 返回拿到的access_token及有效期
     */
    public static AccessToken getAccessToken() throws ClientProtocolException, IOException{
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);//将URL中的两个参数替换掉
        String s = HttpUtils.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(s);

        if(jsonObject!=null){ //如果返回不为空，将返回结果封装进AccessToken实体类
            token.setToken(jsonObject.getString("access_token"));//取出access_token
            token.setExpiresIn(jsonObject.getInteger("expires_in"));//取出access_token的有效期
        }
        return token;
    }

    public static String upload(String filePath,String accessToken,String type) throws IOException {
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            throw new IOException("文件不存在");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=4545&type=image".replace("4545",accessToken).replace("image",type);
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        con.setRequestProperty("Connection","Keep-Alive");
        con.setRequestProperty("Charset","UTF-8");

        String BOUNDARY = "-----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type","multipart/form-data; boundary="+BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition:form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        OutputStream out = new DataOutputStream(con.getOutputStream());

        out.write(head);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes= 0;
        byte[] bufferOut = new byte[1024];
        while((bytes = in.read(bufferOut))!=-1){
            out.write(bufferOut,0,bytes);
        }
        in.close();
        byte[] foot = ("\r\n--"+BOUNDARY + "--\r\n").getBytes("utf-8");
        out.write(foot);
        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result =null;
        try{
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line =null;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
            if (reader==null){
                result = buffer.toString();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                reader.close();
            }
        }
        System.out.println("result"+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject);
        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObject.getString(typeName);
        return mediaId;

    }

    public static void main(String[] args) throws IOException {
        AccessToken accessToken = WeiXinUtil.getAccessToken();
        System.out.println(accessToken.getToken());

        String path ="E:/111.jpg";
        String mediaId = upload(path,accessToken.getToken(),"image");
    }

}
