package qinyan.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import qinyan.pojo.Index;

import java.util.List;

@Service
@CacheConfig(cacheNames = "indexes")
public class IndexService {
    List<Index> indexList;

    //从redis中去获取数据
    @Cacheable(key = "'all_codes'")
    public List<Index> get(){
        Index index=new Index();
        index.setName("无效指数");
        index.setCode("0/0");
        return CollUtil.toList(index);
    }
}
