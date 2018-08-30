package com.ulian168.platform.selenium;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ulian168.platform.selenium.Application;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) throws Exception {
      //-----------------------------打开Chrome浏览器---------------------------------------------
        File file_chrome = new File("/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.driver", file_chrome.getAbsolutePath());
        WebDriver webDriver = new ChromeDriver();// 打开chrome浏览器

        //-----------------------------打开IE浏览器--------------------------------------------------
        //File file_ie = new File("C:\\Program Files\\Internet Explorer\\IEDriverServer.exe");
        //System.setProperty("webdriver.ie.driver", file_ie.getAbsolutePath());
        
        //为 Internet Explorer 设置安全性功能,否则会遇到一个安全问题提示："Protected Mode must be set to the same value (enabled or disabled) for all zones"
        //DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        //caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);  
        //caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        //WebDriver my_dr = new InternetExplorerDriver(caps);// 打开ie浏览器
        
        //WebDriver my_dr = new InternetExplorerDriver();// 打开ie浏览器

        //---------------------------------------------------------------------------------------
        //打开百度
        webDriver.get("https://www.baidu.com");

        Thread.sleep(1000);
        //定位到百度的输入框
        webDriver.findElement(By.id("kw")).sendKeys("G7物流地图");
        
        Thread.sleep(1000);
        //点击搜索
        webDriver.findElement(By.id("su")).click();
        
        Thread.sleep(1000);
        //打印页面标题
        logger.info(webDriver.getTitle());
        //验证页面标题是否符合预期
        Assert.assertEquals(webDriver.getTitle(), "G7物流地图_百度搜索");
        
        Thread.sleep(1000);
        // 关闭所有webdriver进程，退出
        webDriver.quit();
        
        logger.info("验证成功");
    }

}
