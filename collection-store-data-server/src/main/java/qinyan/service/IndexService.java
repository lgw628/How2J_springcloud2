package qinyan.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import qinyan.pojo.Index;
import qinyan.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "indexes") //存储到redis的缓存名称是indexes
public class IndexService {
    //使用restTemple，通过第三方的数据地址，获得数据。map（json）对象
    //再将map对象转为index
    @Autowired
    RestTemplate restTemplate;

    private List<Index> indexList;

    //redis操作
    //清空redis数据
    @CacheEvict(allEntries = true)
    public void remove(){

    }

    //获取redis数据
    @Cacheable(key = "'all_codes'")
    public List<Index> get(){
        return CollUtil.toList();//通过上面的注解，从缓存中获取数据
    }

    //保存到redis
    @Cacheable(key="'all_codes'") //保存到redis使用的key值，是all_codes
    public List<Index> stroe(){
        System.out.println(this);
        return indexList;
    }

    //刷新数据
    @HystrixCommand(fallbackMethod = "thrid_part_no_connect")
    public List<Index> fresh(){
        indexList=fetch_data_from_third();//从三方获取数据
        IndexService indexService= SpringContextUtil.getBean(IndexService.class);//再获取一个service
        indexService.remove();//清空数据
        indexService.stroe();//保存数据
        return indexList;
    }


    public List<Index> fetch_data_from_third(){
        List<Map> maps_data=restTemplate.getForObject("http://127.0.0.1:8090/datas/codes.json",List.class);
        return transfromMapToIndex(maps_data);
    }

    public List<Index> thrid_part_no_connect(){
        System.out.println("第三方数据未连接，断路器启用");
        Index index=new Index("0000","无效代码");
        return CollectionUtil.toList(index);
    }
    public List<Index> transfromMapToIndex(List<Map> thirdMap){
        List<Index> lists=new ArrayList<>();
        for(Map map:thirdMap){
            Index index=new Index();
            String code=map.get("code").toString();
            String name=map.get("name").toString();
            index.setCode(code);
            index.setName(name);
            lists.add(index);
        }
        return lists;
    }

}
