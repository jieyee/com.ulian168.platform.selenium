package com.ulian168.platform.selenium.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.TarUtils;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Controller
public class WebController {
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    
    @Resource
    private ProjectConfig projectConfig;
    
    @Resource
    private TarUtils tarUtils;
    
    @RequestMapping("/nav")
    public String nav(){
        return "nav";
    }
    
    @RequestMapping("/")
    public String home(){
        return "index";
    }
    
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/addTestcase")
    public String addTestcase(){
        return "addTestcase";
    }
    
    @RequestMapping("/editSchedule")
    public String editSchedule(){
        return "editSchedule";
    }
    
    @RequestMapping("/showTestcase")
    public String showTestcase(@RequestParam String caseName, @RequestParam String testcaseDesc, Map<String, Object> model){
        model.put("caseName", caseName);
        model.put("testcaseDesc", testcaseDesc);
        
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        try {
            String content = FileUtils.readFileToString(new File(testCase));
            model.put("content", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "showTestcase";
    }
    
    @RequestMapping("/editTestCase")
    public String editTestCase(@RequestParam String caseName, @RequestParam String testcaseDesc, Map<String, Object> model) {
        model.put("caseName", caseName);
        model.put("testcaseDesc", testcaseDesc);
        
        return "editTestCase";
    }
    
    @RequestMapping("/editTestcaseDetail")
    public String editTestcaseDetail(@RequestParam String row, Map<String, Object> model) {
        model.put("row", row);
        return "editTestcaseDetail";
    }
    
    
    @RequestMapping(value={"/report"},method={RequestMethod.POST,RequestMethod.GET},produces = "text/html; charset=UTF-8")
    public void getReport(final @RequestParam String path, final HttpServletResponse response) throws Exception {
        String filePath = projectConfig.reportPath + File.separator + path;
        ServletOutputStream outputStream = response.getOutputStream();
        String downloadFileName = "report.tar.gz";
        String downloadPath = projectConfig.reportPath + File.separator + "report.tar.gz";
        response.setHeader("Content-disposition", "attachment;filename=" + downloadFileName);
        response.setCharacterEncoding("UTF-8");
        String  extentreports = projectConfig.reportPath + File.separator + "extentreports";
        List<String> inputFileNameList = new ArrayList<String>();
        inputFileNameList.add(filePath);
        inputFileNameList.add(extentreports);
        tarUtils.execute(inputFileNameList, projectConfig.reportPath + File.separator + "report.tar");
        FileUtils.copyFile(new File(downloadPath), outputStream);
    }
}

