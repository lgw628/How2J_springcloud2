package qinyan.util;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;

//这个工具类，实际部署时，是放在本地运行，修改了git之后，就可以运行此工具类，刷新配置
public class FreshConfig {
    public static void main(String[] args) {
        HashMap<String,String> headers =new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        System.out.println("因为要去git获取，还要刷新index-config-server, 会比较卡，所以一般会要好几秒才能完成，请耐心等待");

        String result = HttpUtil.createPost("http://localhost:8041/actuator/bus-refresh").addHeaders(headers).execute().body();
        System.out.println("result:"+result);
        System.out.println("refresh 完成");
    }
}
