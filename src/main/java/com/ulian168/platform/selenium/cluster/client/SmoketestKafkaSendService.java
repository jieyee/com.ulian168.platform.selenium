package com.ulian168.platform.selenium.cluster.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.ulian168.platform.selenium.web.util.ProjectConfig;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class SmoketestKafkaSendService {
    private static final Logger logger = LoggerFactory.getLogger(SmoketestKafkaSendService.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Resource
    private ProjectConfig projectConfig;

    public void sendMessage(final String topic, final String key, final String data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("offset {}", result.getRecordMetadata().offset());
                logger.info("send success {}", result.getProducerRecord().value());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.warn("send fail {}", ex.getMessage());
            }
        });
    }
    
    public void sendMessage(final String data) {
        logger.info("send to default kafka, data:{}", data);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(projectConfig.topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("offset {}", result.getRecordMetadata().offset());
                logger.info("send success {}", result.getProducerRecord().value());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.warn("send fail {}", ex.getMessage());
            }
        });
    }
}
