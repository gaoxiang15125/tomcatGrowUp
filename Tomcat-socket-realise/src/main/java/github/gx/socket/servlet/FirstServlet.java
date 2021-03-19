package github.gx.socket.servlet;

import github.gx.socket.http.GxRequest;
import github.gx.socket.http.GxResponse;
import github.gx.socket.http.GxServlet;

/**
 * @program: TomCatGrowUp
 * @description: 对外提供的服务
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:20
 **/
public class FirstServlet extends GxServlet {


    public void doGet(GxRequest request, GxResponse response) throws Exception {
        this.doPost(request,response);
    }

    public void doPost(GxRequest request, GxResponse response) throws Exception {
        response.write("第一个服务相关内容被调用");
    }
}
