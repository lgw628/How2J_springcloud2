package qinyan;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix //启用断路器

@EnableCaching //开启缓存，使用redis
public class CollectionWithStoreData {
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8001;
        int eurekaServerPort = 8761;
        int redisport=6379;
        port = defaultPort ;
        if(NetUtil.isUsableLocalPort(eurekaServerPort)) {
            System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", eurekaServerPort );
            System.exit(1);
        }

        //判断redis是否已经启动
        if(NetUtil.isUsableLocalPort(redisport)){
            System.err.printf("端口%d未启用，判断redis未启动,故退出服务",redisport);
            System.exit(1);
        }
        if(null!=args && 0!=args.length) {
            for (String arg : args) {
                if(arg.startsWith("port=")) {
                    String strPort= StrUtil.subAfter(arg, "port=", true);
                    if(NumberUtil.isNumber(strPort)) {
                        port = Convert.toInt(strPort);
                    }
                }
            }
        }

        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(CollectionWithStoreData.class).properties("server.port=" + port).run(args);

    }

    //注入resttemple
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //zipkin取样
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
