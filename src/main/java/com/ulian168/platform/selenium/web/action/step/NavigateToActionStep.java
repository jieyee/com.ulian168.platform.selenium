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
@Component(WebConstant.ACTIONSTEP_OPEN)
public class NavigateToActionStep extends WebActionStep {
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_OPEN;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON);
        return metaInf;
    }
    
    @Override
    public int execute(WebContext webContext) {
        WebContext actionContext = webContext.getActionContext();
        try {
            String pageUrl = webContext.getParams();
            WebDriver webDriver = actionContext.getWebDriver();
            uiService.open(webDriver, pageUrl);
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

}
