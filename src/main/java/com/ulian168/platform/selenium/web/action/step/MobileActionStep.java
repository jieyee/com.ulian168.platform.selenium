package com.ulian168.platform.selenium.web.action.step;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 彭杰
 * @since 2018-08-14
 * 
 * 鼠标移动到指定的位置
 */
@Component(WebConstant.ACTIONSTEP_MOBILE)
public class MobileActionStep extends WebActionStep {
    private static final Logger logger = LoggerFactory.getLogger(MobileActionStep.class);
    
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_MOBILE;
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
            String params = webContext.getParams();
            WebDriver webDriver = actionContext.getWebDriver();
            Actions action=new Actions(webDriver);
            logger.info("移动悬浮, locator:{}", params);
            uiService.moveToEle(webDriver, action, params);
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }
}
