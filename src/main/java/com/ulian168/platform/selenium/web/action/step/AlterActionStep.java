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
@Component(WebConstant.ACTIONSTEP_ALERT)
public class AlterActionStep extends WebActionStep {
    @Override
    public String getName() {
        return WebConstant.ACTIONSTEP_ALERT;
    }
    
    @Override
    public JSONObject getMetaInf() {
        JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON);
        metaInf.put("param1", WebConstant.ACTIONSTEP_ALERT_ACCEPT + "|" + WebConstant.ACTIONSTEP_ALERT_DISMISS + "|" + WebConstant.ACTIONSTEP_ALERT_TEXT);
        metaInf.put("paramType2", WebConstant.VO_VAR);
        return metaInf;
    }

    @Override
    public int execute(WebContext webContext) {
        WebContext actionContext = webContext.getActionContext();
        try {
            WebDriver webDriver = actionContext.getWebDriver();
            String params = webContext.getParams();
            String[] paramArray = params.split("\\s");
            String secondStep = paramArray[0];

            if (WebConstant.ACTIONSTEP_ALERT_ACCEPT.equals(secondStep)) {
                logger.info("提示确认");
                uiService.acceptAlert(webDriver);
                return 0;
            }
            if (WebConstant.ACTIONSTEP_ALERT_DISMISS.equals(secondStep)) {
                logger.info("提示取消");
                uiService.dismissAlert(webDriver);
                return 0;
            }
            if (WebConstant.ACTIONSTEP_ALERT_TEXT.equals(secondStep)) {
                String param = paramArray[1];
                String alertTxt = uiService.getAlertTxt(webDriver);
                logger.info("提示文本{}, 变量：{}", alertTxt, param);
                actionContext.put(param, alertTxt);
                return 0;
            }
            String hint = "提示 接受\n" + "提示 取消\n" + "提示 文本 bianliang";
            actionContext.setReason("参数稀烂，重新配置. " + hint);
            return -1;
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            return -1;
        }
    }

}
