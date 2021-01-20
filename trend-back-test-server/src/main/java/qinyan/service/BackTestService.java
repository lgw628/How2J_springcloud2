package qinyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qinyan.client.IndexDataClient;
import qinyan.pojo.IndexData;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
模拟回测业务的实现
 */
@Service
public class BackTestService {
    @Autowired
    IndexDataClient indexDataClient;
    //获取数据
    public List<IndexData> getIndexData(String code){
        List<IndexData> indexDataList=indexDataClient.getIndexData(code);
        Collections.reverse(indexDataList);//反转，让最新数据先显示
        for(IndexData indexData:indexDataList){
            System.out.printf("指数数据日期显示："+indexData.getDate());
        }
        return indexDataList;
    }

}
