package github.gx.socket.http;

import java.io.InputStream;

/**
 * @program: TomCatGrowUp
 * @description: tomcat request 内容定义类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:18
 **/
public class GxRequest {
    private String method;
    private String url;

    public GxRequest(InputStream in){
        try {
            //拿到HTTP协议内容
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = in.read(buff)) > 0) {
                content = new String(buff,0,len);
            }

            String line = content.split("\\n")[0];
            String [] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
            System.out.println(content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

}
