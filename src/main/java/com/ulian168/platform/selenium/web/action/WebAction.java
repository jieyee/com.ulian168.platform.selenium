package com.ulian168.platform.selenium.web.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
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
public class WebAction {
    private static final Logger logger = LoggerFactory.getLogger(WebAction.class);
    private String caseName;
    private WebActionModel webActionModel;
    private WebContext actionContext;
    private List<WebActionStep> actionSteps;
    
    public WebAction init() {
        WebContext context = new WebContext();
        List<WebActionStep> list = new ArrayList<WebActionStep>();
        this.setWebContext(context);
        this.setActionSteps(list);
        return this;
    }
    
    public WebAction init(final WebContext context) {
        List<WebActionStep> list = new ArrayList<WebActionStep>();
        this.setWebContext(context);
        this.setActionSteps(list);
        return this;
    }

    /**
     * @return the webContext
     */
    public WebContext getWebContext() {
        return actionContext;
    }

    /**
     * @param webContext the webContext to set
     */
    public void setWebContext(WebContext webContext) {
        this.actionContext = webContext;
    }

    /**
     * @return the actionSteps
     */
    public List<WebActionStep> getActionSteps() {
        return actionSteps;
    }
    
    /**
     * @param actionSteps the actionSteps to set
     */
    public void setActionSteps(List<WebActionStep> actionSteps) {
        this.actionSteps = actionSteps;
    }

    /**
     * @return the caseName
     */
    public String getCaseName() {
        return caseName;
    }

    /**
     * @param caseName the caseName to set
     */
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    /**
     * @return the webActionModel
     */
    public WebActionModel getWebActionModel() {
        return webActionModel;
    }

    /**
     * @param webActionModel the webActionModel to set
     */
    public void setWebActionModel(WebActionModel webActionModel) {
        this.webActionModel = webActionModel;
    }

    public int execute() {
        int result = 0;
        long t1 = 0L;
        long t2 = 0L;
        long case1 = 0L;
        long case2 = 0L;
        WebContext stepContext = null;
        WebActionStep webActionStep = null;
        
        String fullDesc = null;
        ExtentReports extentReports = actionContext.getExtentReports();
        ExtentTest extentTestTop = actionContext.getExtentTestTop();
        ExtentTest extentTestCase = extentReports.startTest(this.caseName);
        actionContext.setExtentTestCase(extentTestCase);
        logger.info("[{}]测试案例开始执行.", this.caseName);
        try {
            case1 = System.currentTimeMillis();
            
            List<WebActionStepModel> steps = webActionModel.getSteps();
            for (WebActionStepModel step : steps) {
                fullDesc = step.getFullDescription();
                //ExtentTest stepExtentTest = extentReports.startTest(fullDesc, step.getOpParams());
                logger.info("[{}]开始执行", fullDesc);
                t1 = System.currentTimeMillis();
                webActionStep = step.getWebActionStep();
                stepContext = actionContext.getActionStepContext(step);
                result = webActionStep.execute(stepContext);
                t2 = System.currentTimeMillis();
                if (result != 0) {
                    //stepExtentTest.log(LogStatus.FAIL, fullDesc + "执行失败。 原因:", actionContext.getReason());
                    logger.error("[{}]执行失败。耗时:{}ms, 返回码{} 原因{}", fullDesc, (t2-t1), result, actionContext.getReason());
                    return result;
                }
                //stepExtentTest.log(LogStatus.PASS, fullDesc + "执行成功。");
                logger.info("[{}]执行成功。耗时:{}ms", fullDesc, (t2-t1));
            }
        } catch (Exception e) {
            actionContext.setException(e);
            actionContext.setReason(e.getMessage());
            logger.error("错误,", e);
            return -1;
        } finally {
            extentTestTop.appendChild(extentTestCase);
            actionContext.getWebDriver().quit();
            case2 = System.currentTimeMillis();
            logger.info("[{}]执行耗时:{}ms", this.caseName, (case2 - case1));
        }
        return 0;
    }
}
