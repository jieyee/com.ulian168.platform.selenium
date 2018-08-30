package com.ulian168.platform.selenium.web.action.step;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ulian168.platform.selenium.action.ActionStep;
import com.ulian168.platform.selenium.action.ActionStepContainer;
import com.ulian168.platform.selenium.web.service.WebUIService;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public abstract class WebActionStep implements ActionStep {
    protected static final Logger logger = LoggerFactory.getLogger(WebActionStep.class);
    @Resource
    protected WebUIService uiService;
    
    @Resource
    private ActionStepContainer stepContainer;
    
    @PostConstruct
    private void putContainer() {
        stepContainer.put(this.getName(), this);
    }
}
