package github.gx.socket.servlet;

import github.gx.socket.http.GxRequest;
import github.gx.socket.http.GxResponse;
import github.gx.socket.http.GxServlet;

/**
 * @program: TomCatGrowUp
 * @description: 第二个服务，为了演示加载过程
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:22
 **/
public class SecondServlet extends GxServlet {
    public void doGet(GxRequest request, GxResponse response) throws Exception {
        this.doPost(request, response);
    }

    public void doPost(GxRequest request, GxResponse response) throws Exception {
        response.write("第二个服务被调用");
    }
}
