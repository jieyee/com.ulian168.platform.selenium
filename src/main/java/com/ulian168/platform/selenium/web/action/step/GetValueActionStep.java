package com.ulian168.platform.selenium.web.action.step;

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
@Component(WebConstant.ACTIONSTEP_GET)
public class GetValueActionStep extends WebActionStep {

    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_GET;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON + "|" + WebConstant.VO_VAR);
        metaInf.put("param1", WebConstant.VO_CON_NAVIGATOR_TITILE);
        metaInf.put("paramType2", WebConstant.VO_VAR);
        return metaInf;
    }
    
    @Override
    public int execute(WebContext webContext) {
        WebContext actionContext = webContext.getActionContext();
        try {
            String params = webContext.getParams();
            String[] paramArray = params.split("\\s");
            String locator = paramArray[0];
            String param = paramArray[1];
            
            WebDriver webDriver = actionContext.getWebDriver();
            if ("浏览器title".equals(locator)) {
                String title = webDriver.getTitle();
                logger.info("设置到Action上下文, locator:{}, key:{}, value:{}", locator, param, title);
                actionContext.put(param, title);
            } else {
                String value = uiService.getValue(webDriver, locator);
                logger.info("设置到Action上下文, locator:{}, key:{}, value:{}", locator, param, value);
                actionContext.put(param, value);
            }
            
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

}
