/**
　* Copyright 2018-2038 38 ulian168.com
　* 
　* All right reserved.
　*/
package com.ulian168.platform.selenium.web.action.step;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 火狐浏览器驱动
 * @Title:FirefoxWebDriverInitActionStep
 * @Description:TODO
 * @author pengjie
 * @date Aug 27, 2018 10:02:21 AM
 * @version V1.0
 */
@Component(WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_FIREFOX)
public class FirefoxWebDriverInitActionStep extends WebActionStep {

    @Resource
    private ProjectConfig projectConfig;
    
    @Override
    public int execute(WebContext context) {
        WebContext actionContext = context.getActionContext();
        try {
           // System.setProperty("webdriver.gecko.driver", projectConfig.geckodriverPath);
            System.setProperty("webdriver.firefox.bin", projectConfig.firefoxdriverPath);
            WebDriver driver=new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(projectConfig.implicitlyWaitTimeOut, TimeUnit.SECONDS);
            actionContext.setWebDriver(driver);
            Window window = driver.manage().window();
            window.maximize();
            uiService.getWindowFocus(driver, driver.getWindowHandle());
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_FIREFOX;
    }

    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        return metaInf;
    }

}
