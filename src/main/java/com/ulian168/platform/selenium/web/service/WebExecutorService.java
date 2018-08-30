package com.ulian168.platform.selenium.web.service;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.relevantcodes.extentreports.ExtentReports;
import com.ulian168.platform.selenium.bean.ArrangeBean;
import com.ulian168.platform.selenium.cluster.client.SmoketestKafkaSendService;
import com.ulian168.platform.selenium.service.ArrangeService;
import com.ulian168.platform.selenium.web.action.WebAction;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.task.WebUITestCaseTaskQueue;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class WebExecutorService implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(WebExecutorService.class);
    
    @Resource
    private ProjectConfig projectConfig;
    
    @Resource
    private WebUITestCaseReader reader;

    @Resource
    private WebInitService initService;

    @Resource
    private WebUITestCaseTaskQueue taskQueue;
    
    @Resource
    private SmoketestKafkaSendService kafkaSendService;
    
    @Resource
    private WebReportService reportService;
    
    @Autowired
    ArrangeService arrageService;
    
    public WebExecutorService() {
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * 接任务执行.
     */
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
      
        //reportService.createDefaultReports(ctx);
        String fileName =  System.currentTimeMillis() + ".html";
        
      
        int index = 0;
        while (true) {
        	 //logger.info("开始执行");
            try {
            	
                WebActionModel task = taskQueue.getTask();
                if (task == null) {
                	index = 0;
                	//logger.info("任务结束或没有开始");
                	continue;
                }
                index = index + 1;
                if (index == 1) {
                	fileName =  System.currentTimeMillis() + ".html";
            	    ArrangeBean bean = new ArrangeBean();
            	    bean.setJobId(task.getJobId());
            	    bean.setName(task.getRoot());
            	    bean.setServerIp(InetAddress.getLocalHost().getHostAddress());
                 	bean.setState("0");
                 	bean.setPath(task.getRoot());
                 	bean.setReportPath(fileName);
                 	arrageService.insert(bean);
                }
                WebContext ctx = new WebContext();
                WebAction action = initService.init(task, ctx);
                reportService.createReports(ctx, fileName, action.getCaseName());
                action.execute();
                ExtentReports extentReports = ctx.getExtentReports();
                extentReports.flush();
                logger.info("report:" + ctx.getReportPath());
            } catch (Throwable t) {
                logger.error("web测试用例执行失败,{}", t);
            }
        }
        
    }

    /**
     * 立即执行.
     * @param path
     */
    public JSONObject execute(final String path) {
        JSONObject json =  new JSONObject();
        json.put("retMsg", "OK");
        
        WebActionModel model = reader.excuteLoad(path);
        WebContext ctx = new WebContext();
        reportService.createDefaultReports(ctx);
        WebAction action = initService.init(model, ctx);
        int execute = action.execute();
        ExtentReports extentReports = ctx.getExtentReports();
        extentReports.flush();
        if (execute != 0) {
            json.put("retMsg", action.getWebContext().getReason());
        }
        json.put("report", ctx.getReportPath());
        return json;
    }
  
    /**
     * 排队执行.
     * @param name
     */
    public void arrange(final String name, final String root, String jobId) {
        File file = new File(name);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    arrange(file2.getAbsolutePath(), root, jobId);
                } else {
                    arrangeSingleFile(file2, root, jobId);
                }
            }
        } else {
            arrangeSingleFile(file, root, jobId);
        }
    }
    
    public void arrangeSingle(final String fileName, final String root, String jobId) {
        WebActionModel model = reader.load(fileName);
        model.setJobId(jobId);
        model.setRoot(root);
        addTask(model);
    }
    
    public void arrangeSingleFile(final File file, final String root, String jobId) {
        WebActionModel model = reader.excuteLoad(file);
        model.setJobId(jobId);
        model.setRoot(root);
        addTask(model);
    }
    
    /**
     * 根据服务器模式选择安排方案.
     * @param model
     */
    private void addTask(final WebActionModel model) {
        if (projectConfig.clusterFlag) {
            String data = JSONObject.toJSONString(model);
            kafkaSendService.sendMessage(data);
        } else {
            taskQueue.addTask(model);
        }
    }
    
    public void schedule() {
        List<String> schedules = taskQueue.getScheduleList();
        for (String schedule : schedules) {
            String caseFileName = projectConfig.casePath + WebConstant.SLASH + schedule;
            String jobId = "job" + System.currentTimeMillis();
            arrange(caseFileName, schedule, jobId);
        }
        taskQueue.removeAllSchedule();
    }
}
