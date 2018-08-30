package com.ulian168.platform.selenium.web.action.step;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
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
@Component(WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_CHROME)
public class ChromeWebDriverInitActionStep extends WebActionStep {
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_CHROME;
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
        	
        	if (actionContext.getWebDriver() != null) {
        		logger.info("执行默认步骤则不再重复执行.");
        		return 0;
        	}
            System.setProperty("webdriver.chrome.driver", projectConfig.driverPath);
            DesiredCapabilities capabilitie = DesiredCapabilities.chrome();

            capabilitie.setCapability("chrome.switches", projectConfig.chromeswitches); // 屏蔽--ignore-certificate-errors提示
            ChromeOptions options = new ChromeOptions();
            options.addArguments(new String[] { projectConfig.chromeoptions });// 忽略网站证书错误提示

            capabilitie.setCapability(ChromeOptions.CAPABILITY, options);
            ChromeDriver chromeDriver = new ChromeDriver(capabilitie);
            chromeDriver.manage().timeouts().implicitlyWait(projectConfig.implicitlyWaitTimeOut, TimeUnit.SECONDS);
            actionContext.setWebDriver(chromeDriver);
            Window window = chromeDriver.manage().window();
            window.maximize();
            uiService.getWindowFocus(chromeDriver, chromeDriver.getWindowHandle());
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

    
}
