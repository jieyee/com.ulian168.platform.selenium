package com.ulian168.platform.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ulian168.platform.selenium.Application;

public class Test2 {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        // -----------------------------打开Chrome浏览器---------------------------------------------
        //File file_chrome = new File("/usr/local/bin/chromedriver");
        //System.setProperty("webdriver.ie.driver", file_chrome.getAbsolutePath());
        //WebDriver webDriver = new ChromeDriver();// 打开chrome浏览器
        WebDriver webDriver = buildChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            // -----------------------------打开IE浏览器--------------------------------------------------
            // File file_ie = new File("C:\\Program Files\\Internet
            // Explorer\\IEDriverServer.exe");
            // System.setProperty("webdriver.ie.driver",
            // file_ie.getAbsolutePath());

            // 为 Internet Explorer 设置安全性功能,否则会遇到一个安全问题提示："Protected Mode must be
            // set to the same value (enabled or disabled) for all zones"
            // DesiredCapabilities caps =
            // DesiredCapabilities.internetExplorer();
            // caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            // caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
            // WebDriver my_dr = new InternetExplorerDriver(caps);// 打开ie浏览器

            // WebDriver my_dr = new InternetExplorerDriver();// 打开ie浏览器

            // ---------------------------------------------------------------------------------------
            // 打开百度
            webDriver.get("http://localhost:8090/");
            webDriver.manage().window().maximize();
            Thread.sleep(1000);
            // 定位到百度的输入框
            webDriver.findElement(By.name("username")).sendKeys("admin");
            webDriver.findElement(By.name("password")).sendKeys("lsh.0904");
            Thread.sleep(1000);
            // 点击搜索
            webDriver.findElement(By.id("login")).click();

            Thread.sleep(1000);
            // 打印页面标题
            logger.info(webDriver.getTitle());
            // 验证页面标题是否符合预期
            Assert.assertEquals(webDriver.getTitle(), "基于Docker的冒烟环境...");
            
            webDriver.findElement(By.xpath("//*[@id=\\\"example\\\"]/tbody/tr[1]/td[3]/div/a[1]")).click();

            Thread.sleep(1000);

            logger.info("验证成功");
        } catch (Exception e) {

        } finally {
            // 关闭所有webdriver进程，退出
            if (webDriver != null) {
                webDriver.findElement(By.xpath("//*[@id=\"nav\"]/nav/form/button")).click();
                webDriver.quit();
                logger.info("关闭窗口.");
            }
        }
    }
    
    public static WebDriver buildChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        DesiredCapabilities capabilitie = DesiredCapabilities.chrome();

        capabilitie.setCapability("chrome.switches", "--incognito"); // 屏蔽--ignore-certificate-errors提示
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[]{"--test-type"});// 忽略网站证书错误提示

        capabilitie.setCapability(ChromeOptions.CAPABILITY, options);

        return new ChromeDriver(capabilitie);

    }
}