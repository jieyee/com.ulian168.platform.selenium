package com.ulian168.platform.selenium.web.action.step;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component(WebConstant.ACTIONSTEP_SET)
public class SetValueActionStep extends WebActionStep {
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_SET;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON + "|" + WebConstant.VO_VAR);
        metaInf.put("paramType2", WebConstant.VO_CON);
        return metaInf;
    }
    
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
                uiService.sentKey(webDriver, locator, value);
            }
            
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

}
