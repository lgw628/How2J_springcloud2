package qinyan.job;

import cn.hutool.core.date.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import qinyan.pojo.Index;
import qinyan.service.IndexDataService;
import qinyan.service.IndexService;

import java.util.List;

/*
任务类，
定时去刷新数据
 */
public class DataSynJob extends QuartzJobBean {

    @Autowired
    IndexDataService indexDataService;

    @Autowired
    IndexService indexService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时启动："+ DateUtil.now());
        List<Index> indexList=indexService.fresh();//刷新指数数据
        for(Index index:indexList){
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时器结束："+DateUtil.now());
    }
}
