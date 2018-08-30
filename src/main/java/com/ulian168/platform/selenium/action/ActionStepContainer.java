package com.ulian168.platform.selenium.action;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.web.action.step.WebActionStep;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class ActionStepContainer extends HashMap<String, ActionStep>{
    private static final long serialVersionUID = 1L;

    public ActionStep getActionStep(final String prefix, final String name) {
        ActionStep actionStep = this.get(prefix + "." + name);
        return actionStep;
    }
    
    public void setActionStep(final String prefix, final String name, final ActionStep actionStep) {
        this.put(prefix + "." + name, actionStep);
    }
    
    public WebActionStep getWebActionStep(final String name) {
        ActionStep actionStep = this.getActionStep("web", name);
        if (actionStep == null) {
            return null;
        }
        return (WebActionStep) actionStep;
    }
    
    public void setWebActionStep(final String name, final ActionStep actionStep) {
        this.setActionStep("web", name, actionStep);
    }
}
