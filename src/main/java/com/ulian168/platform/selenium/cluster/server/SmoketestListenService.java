package com.ulian168.platform.selenium.cluster.server;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.task.WebUITestCaseTaskQueue;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@EnableKafka
@Configuration
public class SmoketestListenService {
    private static final Logger logger = LoggerFactory.getLogger(SmoketestListenService.class);
    
    @Resource
    private WebUITestCaseTaskQueue taskQueue; 
    
    @KafkaListener(topics = "${ulian168.kafka.topic}")
    public void processMessage(ConsumerRecord<String, String> record) {
        String value = record.value();
        logger.info("topic {}, offset {}, key {}, value {}", record.topic(), record.offset(), record.key(), value);
        JSONObject json = (JSONObject) JSON.parse(value);
        WebActionModel model = JSONObject.toJavaObject(json, WebActionModel.class);
        taskQueue.addTask(model);
    }
}
