package github.gx.socket.http;

import java.io.OutputStream;

/**
 * @program: TomCatGrowUp
 * @description: tomcat Response 信息定义类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:19
 **/
public class GxResponse {
    private OutputStream out;
    public GxResponse(OutputStream out){
        this.out = out;
    }

    public void write(String s) throws Exception {
        //用的是HTTP协议，输出也要遵循HTTP协议
        //给到一个状态码 200
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }
}
