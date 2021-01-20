package qinyan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import qinyan.pojo.IndexData;

import java.util.List;


//指明要获取数据的微服务，。发生熔断，要去实现类找
@FeignClient(value = "index-data-service",fallback = IndexDataClientImpFeign.class)
public interface IndexDataClient {
    @GetMapping("/data/{code}")
    public List<IndexData> getIndexData(@PathVariable("code")String code);
}
