package com.ulian168.platform.selenium.web.action.step;

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
@Component(WebConstant.ACTIONSTEP_EQUALS)
public class EqualsActionStep extends WebActionStep {
    private static final Logger logger = LoggerFactory.getLogger(EqualsActionStep.class);
    
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_EQUALS;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_VAR);
        metaInf.put("paramType2", WebConstant.VO_VAR);
        return metaInf;
    }
    
    @Override
    public int execute(WebContext webContext) {
        WebContext actionContext = webContext.getActionContext();
        try {
            String params = webContext.getParams();
            String[] paramArray = params.split("\\s");
            String a = paramArray[0];
            String b = paramArray[1];
            
            Object o1 = actionContext.get(a);
            Object o2 = actionContext.get(b);
            
            boolean result = false;
            if ((o1 == null && o2 == null) || o1.equals(o2)) {
                result = true;
                logger.info("上下文, {}={},{}={}, 是否相等:{}", a, o1, b, o2, result);
                return 0;
            }
            logger.info("上下文, {}={},{}={}", a, o1, b, o2, result);
            return -1;
            
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -2;
        }
    }
}
