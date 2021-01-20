package qinyan.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/*
此类，用于获取当前服务的端口号
 */
@Component
public class IpConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort=webServerInitializedEvent.getWebServer().getPort();
    }
    //在web服务器初始化时进行监听
    public int getPort(){
        return this.serverPort;
    }
}
