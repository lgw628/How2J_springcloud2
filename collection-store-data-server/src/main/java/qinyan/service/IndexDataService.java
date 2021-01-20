package qinyan.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONArray;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qinyan.pojo.IndexData;
import qinyan.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
redis数据保存说明：
fresh刷新方法：先从另一个微服务获得对应地址参数code的数据，
——》然后将code和得到的所有数据 保存到一个map对象中
——》如果redis中有这个map对象，删除
——》使用store方法将这个map对象更新到redis中
 */
@Service
@CacheConfig(cacheNames = "index_datas") //在redis中的分组
public class IndexDataService {
    @Autowired
    RestTemplate restTemplate;
    Map<String,List<IndexData>> allIndexDataList=new HashMap<>();//存放所有的指数数据，使用

    //三方获取指数数据
    public List<IndexData> fetch_third_indexData(String code){
        List<Map> mapList=restTemplate.getForObject("http://127.0.0.1:8090/datas/"+code+".json",List.class);
        /*JSONArray jsonArray=new JSONArray();//转换的中间量
        jsonArray.addAll(mapList);
        List<IndexData> list=jsonArray.toJavaList(IndexData.class);*/
        return transformMapToT(mapList);
    }
    //数据转换
    public List<IndexData> transformMapToT(List<Map> maps){
        List<IndexData> indexDataList=new ArrayList<>();
        for(Map map:maps){
            IndexData indexData=new IndexData();
            String date=map.get("date").toString();
            float  closePoint= Convert.toFloat(map.get("closePoint"));
            indexData.setDate(date);
            indexData.setClosePoint(closePoint);
            indexDataList.add(indexData);
        }
        return indexDataList;
    }
    //保存数据。对应key。更新
    @CachePut(key = "'indexdata-code-'+ #p0")//前缀+参数
    public List<IndexData> store(String code){
        return allIndexDataList.get(code);//将code对应的所有数据保存redis
    }

    //获取数据
    @Cacheable(key = "'indexdata-code-'+ #p0")
    public List<IndexData> get(String code){
        return CollUtil.toList(); //从缓存中拿到数据
    }

    //清空redis分组index_datas中对应键值对的数据缓存数据
    @CacheEvict(key = "'indexdata-code-'+ #p0")
    public void remove(String code){

    }

    //刷新数据
    @HystrixCommand(fallbackMethod = "third_part_no_connect")
    public List<IndexData> fresh(String code){
        //通过地址拿到对应指数的数据
        List<IndexData> indexDataCode=fetch_third_indexData(code);//拿到对应code的数据
        allIndexDataList.put(code,indexDataCode);//存入一个code对应的所有数据
        System.out.println("code"+code);
        System.out.println("数据长度："+allIndexDataList.get(code).size());

        IndexDataService indexDataService= SpringContextUtil.getBean(IndexDataService.class);
        indexDataService.remove(code);
        return indexDataService.store(code);
    }

    //熔断方法需要与被注解方法，有一致类型的参数和返回值
    public List<IndexData> third_part_no_connect(String code){
        System.out.println("third_part_no_connect");
        IndexData index= new IndexData();
        index.setClosePoint(0);
        index.setDate("n/a");
        return CollectionUtil.toList(index);
    }
}
