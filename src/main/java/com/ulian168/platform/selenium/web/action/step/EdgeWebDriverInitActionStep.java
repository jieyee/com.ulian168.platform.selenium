/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.web.action.step;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * @Title:EdgeWebDriverInitActionStep
 * @Description:edge浏览器驱动初始化
 * @author liuming
 * @date 2018年8月27日 上午9:58:20
 * @version V1.0
 */
@Component(WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_EDGE)
public class EdgeWebDriverInitActionStep extends WebActionStep {
	@Resource
	private ProjectConfig projectConfig;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	@Override
	public int execute(WebContext context) {
		WebContext actionContext = context.getActionContext();
		try {
			if (actionContext.getWebDriver() != null) {
				logger.info("执行默认步骤则不再重复执行.");
				return 0;
			}
			System.setProperty("webdriver.edge.driver", projectConfig.edgeDriverPath);
			DesiredCapabilities capabilitie = DesiredCapabilities.edge();
			EdgeDriver edgeDriver = new EdgeDriver(capabilitie);
			edgeDriver.manage().timeouts().implicitlyWait(projectConfig.implicitlyWaitTimeOut, TimeUnit.SECONDS);
			actionContext.setWebDriver(edgeDriver);
			Window window = edgeDriver.manage().window();
			window.maximize();
			uiService.getWindowFocus(edgeDriver, edgeDriver.getWindowHandle());
		} catch (Exception e) {
			actionContext.setException(e);
			actionContext.setReason(e.getMessage());
			return -1;
		}
		return 0;
	}

	@Override
	public String getName() {
		return WebConstant.ACTIONSTEP_USE + WebConstant.BROWSER_EDGE;
	}

	@Override
	public JSONObject getMetaInf() {
		JSONObject metaInf = new JSONObject();
		metaInf.put("name", this.getName());
		return metaInf;
	}

}
