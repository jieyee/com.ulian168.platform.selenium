package com.ulian168.platform.selenium;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ulian168.platform.selenium.web.service.WebExecutorService;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan
public class Application extends WebMvcConfigurerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static ConfigurableApplicationContext context;
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver(); 
        //设置默认区域
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }

    public static void main(String[] args) {
        Application.context = SpringApplication.run(Application.class, args);
        logger.info("selenium服务启动...");
        //demo();
    }

    private static void demo() {
        WebExecutorService service = Application.context.getBean(WebExecutorService.class);
        // bean.execute();
        String path = "/Users/jieyee/workspaces/lshworkspace/com.ulian168.platform.selenium/src/main/resources/testcases";
        service.arrange(path,path,"job"+System.currentTimeMillis());
    }
}
