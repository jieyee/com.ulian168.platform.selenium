package com.ulian168.platform.selenium.web.action.step;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component(WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_SAFARI)
public class SafariWebDriverInitActionStep extends WebActionStep {
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_SAFARI;
    }

    @Resource
    private ProjectConfig projectConfig;
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        return metaInf;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ulian168.platform.selenium.web.step.WebStep#action(com.ulian168.platform.
     * selenium.web.context.WebContext)
     */
    @Override
    public int execute(WebContext context) {
        WebContext actionContext = context.getActionContext();
        try {
            WebDriver driver = new SafariDriver();
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

    
}
