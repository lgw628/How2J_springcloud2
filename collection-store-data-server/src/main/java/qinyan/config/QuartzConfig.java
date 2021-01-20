package qinyan.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import qinyan.job.DataSynJob;

/*
定时器配置
 */
@Configuration
public class QuartzConfig {
    //时间。类常量
    private static final int interval=1;

    //注入bean
    @Bean
    public JobDetail weatherDataSyncJobDetail() {
        return JobBuilder.newJob(DataSynJob.class).withIdentity("dataSynJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger weatherDataSyncTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                //.withIntervalInHours(interval).repeatForever();
                .withIntervalInMinutes(interval).repeatForever(); //使用时间间隔--分钟，小时

        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
                .withIdentity("indexDataSyncTrigger").withSchedule(schedBuilder).build();
    }
}

