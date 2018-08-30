package com.ulian168.platform.selenium.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
@ConfigurationProperties
public class ProjectConfig {
    @Value("${platform.casePath:/Users/jieyee/workspaces/lshworkspace/com.ulian168.platform.selenium/src/main/resources/testcases}")
    public String casePath;
    
    @Value("${platform.navigator:}")
    public String navigator;
    
    @Value("${platform.reportPath:/Users/jieyee/tmp}")
    public String reportPath;
    
    @Value("${ulian168.configuration.explicitWaitTimeOut}")
    public long explicitWaitTimeOut;
    
    @Value("${ulian168.configuration.implicitlyWaitTimeOut}")
    public long implicitlyWaitTimeOut;
    
    @Value("${ulian168.chrome.driver}")
    public String driverPath;
    
    @Value("${ulian168.chrome.switch}")
    public String chromeswitches;
    
    @Value("${ulian168.chrome.options}")
    public String chromeoptions;
    
    @Value("${ulian168.kafka.topic}")
    public String topic;
    
    @Value("${ulian168.cluster}")
    public boolean clusterFlag;
    
    @Value("${ulian168.ie.driver}")
    public String iedriverPath;
    
    @Value("${ulian168.edge.driver}")
    public String edgeDriverPath;
    
    @Value("${ulian168.geckodriver.driver}")
    public String geckodriverPath;
    
    @Value("${ulian168.firefix.driver}")
    public String firefoxdriverPath;
    
}
