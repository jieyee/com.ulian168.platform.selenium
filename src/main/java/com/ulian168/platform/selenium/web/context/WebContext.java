package com.ulian168.platform.selenium.web.context;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.model.WebActionStepModel;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public class WebContext extends HashMap<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(WebContext.class);

    private static final long serialVersionUID = 1L;
    private static final String WEBDRIVER = "WebContext.webDriver";
    private static final String EXCEPTION = "WebContext.exception";
    private static final String REASON = "WebContext.reason";
    private static final String PARAMS = "WebContext.opParams";
    private static final String STEPMODEL = "WebContext.stepmodel";
    private static final String ACTIONCONTEXT = "WebContext.Action";
    private static final String PATHPIC = "WebContext.path.pic";
    
    private static final String REPORTS = "WebContext.report.path";
    private static final String EXTENT_REPORTS = "WebContext.report.extentReports";
    private static final String EXTENT_TOP_LEVEL = "WebContext.report.top";
    private static final String EXTENT_CASE_LEVEL = "WebContext.report.case";
    private static final String EXTENT_STEP_LEVEL = "WebContext.report.step";

    public void setException(final Exception e) {
        this.put(EXCEPTION, e);
    }

    public void setWebDriver(final WebDriver webDriver) {
        this.put(WEBDRIVER, webDriver);
    }

    public WebDriver getWebDriver() {
        Object object = this.get(WEBDRIVER);
        if (object == null) {
            return null;
        }
        return (WebDriver) object;
    }

    public Exception getException() {
        Object object = this.get(EXCEPTION);
        if (object == null) {
            return null;
        }
        return (Exception) object;
    }

    public void setReason(final String reason) {
        this.put(REASON, reason);
    }

    public String getReason() {
        Object object = this.get(REASON);
        if (object == null) {
            return null;
        }
        return (String) object;
    }

    public void setParams(final String params) {
        this.put(PARAMS, params);
    }

    public String getParams() {
        Object object = this.get(PARAMS);
        if (object == null) {
            return null;
        }
        return (String) object;
    }
    
    public void setWebActionStepModel(final WebActionStepModel stepModel) {
        this.put(STEPMODEL, stepModel);
    }
    
    public WebActionStepModel getWebActionStepModel() {
        Object object = this.get(STEPMODEL);
        if (object == null) {
            return null;
        }
        return (WebActionStepModel) object;
    }
    
    public void setActionStepContext(final WebActionStepModel stepModel, final WebContext context) {
        this.put(stepModel, context);
    }
    
    public WebContext getActionStepContext(final WebActionStepModel stepModel) {
        Object object = this.get(stepModel);
        if (object == null) {
            return null;
        }
        return (WebContext) object;
    }
    
    public void setActionContext(final WebActionModel actionModel, final WebContext context) {
        this.put(actionModel, context);
    }
    
    public WebContext getActionContext(final WebActionModel actionModel) {
        Object object = this.get(actionModel);
        if (object == null) {
            return null;
        }
        return (WebContext) object;
    }
    
    /**
     * webcontext分2层，一层action，一层actionstep.<br>
     * 默认情况下 webcontext，只存疑个action的顶层context。
     * @param context
     */
    public void setActionContext(final WebContext context) {
        this.put(ACTIONCONTEXT, context);
    }
    
    public WebContext getActionContext() {
        Object object = this.get(ACTIONCONTEXT);
        if (object == null) {
            return null;
        }
        return (WebContext) object;
    }

    public void setPathPic(final String path) {
        this.put(PATHPIC, path);
    }
    
    public String getPathPic() {
        Object object = this.get(PATHPIC);
        if (object == null) {
            return null;
        }
        return (String) object;
    }
    
    public void setExtentReports(final ExtentReports extentReports) {
        this.put(EXTENT_REPORTS, extentReports);
    }
    
    public ExtentReports getExtentReports() {
        Object object = this.get(EXTENT_REPORTS);
        if (object == null) {
            return null;
        }
        return (ExtentReports) object;
    }
    
    public void setExtentTestTop(final ExtentTest extentTest) {
        this.put(EXTENT_TOP_LEVEL, extentTest);
    }
    
    public ExtentTest getExtentTestTop() {
        Object object = this.get(EXTENT_TOP_LEVEL);
        if (object == null) {
            return null;
        }
        return (ExtentTest) object;
    }
    
    public void setExtentTestCase(final ExtentTest extentTest) {
        this.put(EXTENT_CASE_LEVEL, extentTest);
    }
    
    public ExtentTest getExtentTestCase() {
        Object object = this.get(EXTENT_CASE_LEVEL);
        if (object == null) {
            return null;
        }
        return (ExtentTest) object;
    }
    
    public void setExtentTestStep(final ExtentTest extentTest) {
        this.put(EXTENT_STEP_LEVEL, extentTest);
    }
    
    public ExtentTest getExtentTestStep() {
        Object object = this.get(EXTENT_STEP_LEVEL);
        if (object == null) {
            return null;
        }
        return (ExtentTest) object;
    }
    
    public void setReportPath(final String path) {
        this.put(REPORTS, path);
    }
    
    public String getReportPath() {
        Object object = this.get(REPORTS);
        if (object == null) {
            return null;
        }
        return (String) object;
    }
}
