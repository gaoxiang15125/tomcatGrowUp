package github.gx.socket;

import github.gx.socket.http.GxRequest;
import github.gx.socket.http.GxResponse;
import github.gx.socket.http.GxServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: TomCatGrowUp
 * @description: 简单的tomcat 实现代码，包括 解析 servlet 配置信息、初始化 servlet对象，通过 socket 监听端口
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-19 14:24
 **/
public class GxTomcat {

    /**
     * 本次实现方式属于 BIO，按顺序一个个处理用户请求
     */
    private ServerSocket server;
    private Map<String, GxServlet> servletMapping = new HashMap<String, GxServlet>();

    private Properties webConfig = new Properties();
    private int port = 8000;

    /**
     * 读取配置文件，初始化 servlet 信息
     */
    private void init() {
        //加载web.xml文件,同时初始化 ServletMapping对象
        try{
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webConfig.load(fis);

            for (Object k : webConfig.keySet()) {
                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webConfig.getProperty(key);
                    String className = webConfig.getProperty(servletName + ".className");
                    //单实例，多线程
                    GxServlet obj = (GxServlet)Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                } else if(key.endsWith("port")) {
                    port = Integer.parseInt(webConfig.getProperty(key));
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 启动 servlet 服务器
     */
    public void start(){

        //1、加载配置文件，初始化 ServletMapping
        init();

        try {
            server = new ServerSocket(this.port);

            System.out.println("GP Tomcat 已启动，监听的端口是：" + this.port);
            /**
             * 从这里的逻辑可以看出，属于 BIO
             */
            //2、等待用户请求,用一个死循环来等待用户请求
            while (true) {
                Socket client = server.accept();
                //4、HTTP请求，发送的数据就是字符串，有规律的字符串（HTTP协议）
                process(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理 socket 接受的数据信息，通过代理调用对应的方法，并对结果进行返回
     * @param client 存储接受信息的 套接字
     * @throws Exception
     */
    private void process(Socket client) throws Exception {

        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        //7、Request(InputStrean)/Response(OutputStrean)
        GxRequest request = new GxRequest(is);
        GxResponse response = new GxResponse(os);

        //5、从协议内容中拿到URL，把相应的Servlet用反射进行实例化
        String url = request.getUrl();

        if(servletMapping.containsKey(url)){
            //6、调用实例化对象的service()方法，执行具体的逻辑doGet/doPost方法
            servletMapping.get(url).service(request,response);
        }else{
            response.write("404 - Not Found");
        }

        // TODO 具体的 数据发送过程 经过了 java.net 封装

        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new GxTomcat().start();
    }
}
