package com.ulian168.platform.selenium.web.action.step;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component(WebConstant.ACTIONSTEP_SHOT)
public class ScreenCaptureActionStep extends WebActionStep {
    private static final Logger logger = LoggerFactory.getLogger(ScreenCaptureActionStep.class);
    
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_SHOT;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        return metaInf;
    }
    
    @Override
    public int execute(WebContext webContext) {
        WebContext actionContext = webContext.getActionContext();
        try {
            WebDriver webDriver = actionContext.getWebDriver();
            String picName = uiService.screenCapture(webDriver);
            actionContext.setPathPic(picName);
            logger.info("截图成功, 地址:{}", picName);
            return 0;
            
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -2;
        }
    }
}
