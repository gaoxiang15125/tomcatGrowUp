package github.gx.socket.http;

/**
 * @program: TomCatGrowUp
 * @description: Servlet 抽象类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:17
 **/
public abstract class GxServlet {
    public void service(GxRequest request, GxResponse response) throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet(GxRequest request, GxResponse response) throws Exception;
    public abstract void doPost(GxRequest request, GxResponse response) throws Exception;
}
