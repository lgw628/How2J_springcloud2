package qinyan.service;

import cn.hutool.core.collection.CollUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import qinyan.pojo.IndexData;

import java.util.List;

@Service
@CacheConfig(cacheNames="index_datas")
public class IndexDataService {
    @Cacheable(key="'indexdata-code-'+ #p0")
    public List<IndexData> get(String code){
        return CollUtil.toList();
    }
}
