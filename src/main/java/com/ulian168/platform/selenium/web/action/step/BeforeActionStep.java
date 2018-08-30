/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.web.action.step;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * @Title:BeforeActionStep
 * @Description:执行前置事件
 * @author liuming
 * @date 2018年8月10日 上午10:56:38
 * @version V1.0
 */
@Component(WebConstant.ACTIONSTEP_EVENT_BEFORE)
public class BeforeActionStep extends WebActionStep {
	private static final Logger logger = LoggerFactory.getLogger(BeforeActionStep.class);
	@Autowired
	ChromeWebDriverInitActionStep chrome;
	
	@Autowired
	IeWebDriverInitActionStep ie;
	
	@Autowired
	EdgeWebDriverInitActionStep edge;
	
	@Autowired
	FirefoxWebDriverInitActionStep firefox;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
    @Resource
    private ProjectConfig projectConfig;

	@Override
	public int execute(WebContext context) {
		logger.info("前置事件开始执行");
		int result = 0;
		//默认执行浏览器
		String navigator = projectConfig.navigator;
		if(!StringUtils.isEmpty(navigator)) {
			WebContext actionContext = context.getActionContext();
			//如果浏览器是chrome
			navigator = navigator.trim().toLowerCase();
			if(navigator.equalsIgnoreCase("chrome")) {
				result = chrome.execute(context);
			} else if(navigator.equalsIgnoreCase("ie")) {
				result = ie.execute(context);
			} else if(navigator.equalsIgnoreCase("firefox")) {
				result = firefox.execute(context);
			} else if(navigator.equalsIgnoreCase("edge")) {
				result = edge.execute(context);
			}
		}
		
		logger.info("前置事件执行完成");
        return result;
	}

	@Override
	public String getName() {
		return WebConstant.ACTIONSTEP_EVENT_BEFORE;
	}

	@Override
	public JSONObject getMetaInf() {
		JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON);
        return metaInf;
	}

}
