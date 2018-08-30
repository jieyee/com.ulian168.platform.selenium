package com.ulian168.platform.selenium.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.Application;
import com.ulian168.platform.selenium.action.ActionStepContainer;
import com.ulian168.platform.selenium.web.action.WebAction;
import com.ulian168.platform.selenium.web.action.step.WebActionStep;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.model.WebActionStepModel;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class WebInitService {
    private static final Logger logger = LoggerFactory.getLogger(WebInitService.class);
    @Resource
    private ActionStepContainer stepContainer;
    
    public WebAction init(final WebActionModel webActionModel) {
        WebAction action = new WebAction();
        action.setWebActionModel(webActionModel);
        List<WebActionStep> list = action.init().getActionSteps();
        WebContext actionContext = action.getWebContext();
        
        List<WebActionStepModel> steps = webActionModel.getSteps();
        for (WebActionStepModel model : steps) {
            WebActionStep webActionStep = getActionStep(actionContext, model);
            list.add(webActionStep);
            if (StringUtils.isEmpty(action.getCaseName())) {
                action.setCaseName(model.getCaseName());
            }
        }
        return action;
    }
    
    public WebAction init(final WebActionModel webActionModel, final WebContext context) {
        WebAction action = new WebAction();
        action.setWebActionModel(webActionModel);
        List<WebActionStep> list = action.init(context).getActionSteps();
        WebContext actionContext = action.getWebContext();
        
        List<WebActionStepModel> steps = webActionModel.getSteps();
        for (WebActionStepModel model : steps) {
            WebActionStep webActionStep = getActionStep(actionContext, model);
            list.add(webActionStep);
            if (StringUtils.isEmpty(action.getCaseName())) {
                action.setCaseName(model.getCaseName());
            }
        }
        return action;
    }
    
    /**
     * 擦亮眼睛，看清楚在干什么.
     * @param actionContext
     * @param model
     * @return
     */
    private WebActionStep getActionStep(final WebContext actionContext, final WebActionStepModel model) {
        String opName = model.getOpName();
        String opParams = model.getOpParams();
        
        //ChromeWebDriverInitActionStep step = Application.context.getBean(ChromeWebDriverInitActionStep.class);
        WebActionStep webActionStep = (WebActionStep)Application.context.getBean(opName);
        model.setWebActionStep(webActionStep);
        WebContext stepContext = new WebContext();
        stepContext.setParams(opParams);
        stepContext.setActionContext(actionContext);
        stepContext.setWebActionStepModel(model);
        actionContext.setActionStepContext(model, stepContext);
        return webActionStep;
    }
}
