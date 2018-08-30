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
@Component(WebConstant.ACTIONSTEP_CLOSEWINDOW)
public class CloseWindowActionStep extends WebActionStep {
    private static final Logger logger = LoggerFactory.getLogger(CloseWindowActionStep.class);
    
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_CLOSEWINDOW;
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
            logger.info("关闭窗口:{}", params);
            uiService.closeNotMainWindow(webDriver, params);
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }
}
