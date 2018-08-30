package com.ulian168.platform.selenium.web.service;

import java.io.File;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.NetworkMode;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.ProjectConfig;

@Component
public class WebReportService {
private static final Logger logger = LoggerFactory.getLogger(WebReportService.class);
    
    @Resource
    private ProjectConfig projectConfig;
    
    public void createReports(final WebContext context, String pfileName, String pnodeName) {
        String fileName = pfileName;
        String nodeName = pnodeName;
        logger.info("fileName: {}, nodeName: {}", fileName, nodeName);
        if (StringUtils.isEmpty(fileName)) {
            fileName = System.currentTimeMillis() + ".html";
            logger.info("生成报告动态文件: {}", fileName);
        }
        
        if (StringUtils.isEmpty(nodeName)) {
            nodeName = System.currentTimeMillis() + "";
            logger.info("生成动态节点: {}", nodeName);
        }
        String report = projectConfig.reportPath + File.separator + fileName;
        ExtentReports extentReports = new ExtentReports(report, false, NetworkMode.OFFLINE, new Locale("cn"));
        ExtentTest extentTest = extentReports.startTest(nodeName);
        
        context.setReportPath(fileName);
        context.setExtentReports(extentReports);
        context.setExtentTestTop(extentTest);
    }
    
    public void createDefaultReports(final WebContext context) {
        createReports(context, null, null);
    }
    
    //TODO: 单独只有报告无节点的代码
    
}
