package com.ulian168.platform.selenium.web.action.step;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
 * 不存在
 * 
 * 判断页面是否存在传入的信息.存在为true -1,不存在false 0
 */
@Component(WebConstant.ACTIONSTEP_NOPRESENCE)
public class NoPresenceActionStep extends WebActionStep {

    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_NOPRESENCE;
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
            
            WebDriver webDriver = actionContext.getWebDriver();
            boolean contains = webDriver.findElement(By.tagName("body")).getText().contains(locator);
            if (contains) {
                return -1;
            } 

        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
        return 0;
    }

}
