package com.ulian168.platform.selenium.web.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.web.service.WebExecutorService;

/**
 * 自动化冒烟平台.<br>
 * 计划任务.
 * @author 周明
 * @since 2017-12-09
 */
@Component
@EnableScheduling
public class WebSchedulingTask {
    private static final Logger logger = LoggerFactory.getLogger(WebSchedulingTask.class);
    @Resource
    private WebExecutorService executorService;
    
    //@Scheduled(cron="0 0 1 * * *")
    @Scheduled(cron = "0 0/1 * * * ? ")
//    @Scheduled(cron="30 30 * * * *")
    public void synchronize(){
        logger.info("安排冒烟计划开始...");
        executorService.schedule();
        logger.info("安排冒烟计划结束...");
    }
}
