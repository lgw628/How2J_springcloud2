package qinyan.client;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Component;
import qinyan.pojo.IndexData;

import java.util.List;

@Component
public class IndexDataClientImpFeign implements IndexDataClient{
    @Override
    public List<IndexData> getIndexData(String code) {
        IndexData indexData=new IndexData();
        indexData.setDate("0/0,无效代码");
        indexData.setClosePoint(0);
        return CollectionUtil.toList(indexData);
    }
}
