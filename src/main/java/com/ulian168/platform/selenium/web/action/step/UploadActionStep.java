package com.ulian168.platform.selenium.web.action.step;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.WebConstant;

@Component(WebConstant.ACTIONSTEP_UPLOAD)
public class UploadActionStep extends WebActionStep{
	private static final Logger logger = LoggerFactory.getLogger(UploadActionStep.class);

	@Override
	public int execute(WebContext webContext) {
		WebContext actionContext = webContext.getActionContext();
        try {
            String params = webContext.getParams();

            String[] paramArray = params.split("\\s");
            String locator = paramArray[0];
            String value = paramArray[1];
            if (value.startsWith("\"")) {
                value = StringUtils.substringBetween(params, "\"",  "\"");
            }
            if (locator.startsWith("[")) {
                locator = locator.substring(1, locator.length() - 1);
                logger.info("设置到Action上下文, key:{}, value:{}", locator, value);
                actionContext.put(locator, value);
            } else {
                WebDriver webDriver = actionContext.getWebDriver();
                logger.info("发送到web上, locator:{}, value:{}", locator, value);
                uiService.upload(webDriver, locator, value);
            }
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
	}

	@Override
	public String getName() {
		return WebConstant.ACTIONSTEP_UPLOAD;
	}

	@Override
	public JSONObject getMetaInf() {
		JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON);
        return metaInf;
	}
}
