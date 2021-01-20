package qinyan;

import brave.sampler.Sampler;
import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //表示它是一个启动类
@EnableEurekaServer //表示它是注册中的服务器
public class EurekaServer {
    public static void main(String[] args) {
        //判断端口号是否被占用
        int port=8761; //这个是注册中心 服务器的默认端口。
        if(!NetUtil.isUsableLocalPort(port)){
            System.err.printf("注册中心服务器端口%d已被占用，无法启动",port);//打印端口不可用信息
            System.exit(1); //退出控制台
        }
        //使用springapplicationbuilder启动应用(并设置启动的端口号)
        new SpringApplicationBuilder(EurekaServer.class).properties("server.port="+port).run(args);
    }

}
