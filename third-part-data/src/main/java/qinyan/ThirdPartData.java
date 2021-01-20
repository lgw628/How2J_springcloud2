package qinyan;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient //注册为微服务
public class ThirdPartData {
    public static void main(String[] args) {
        int eurekaServerPort=8761;
        int port=8090;
        //判断eureka是否已经启动
        if(NetUtil.isUsableLocalPort(eurekaServerPort)){
            System.err.printf("端口%d未启用，判断是eureka-server服务未启动，本服务无法使用，故退出%n",eurekaServerPort);
            System.exit(1);
        }

        //看启动是否带了参数，带参则使用此参数（端口号），使得在启动时，使用不同参数就可以了。
        if(args!=null && args.length!=0){
            for (String arg : args) {
                if(arg.startsWith("port=")) {
                    String strPort= StrUtil.subAfter(arg, "port=", true);
                    if(NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        //判断启动端口是否可用
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        //使用springapplicationbuilder启动应用
        new SpringApplicationBuilder(ThirdPartData.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
