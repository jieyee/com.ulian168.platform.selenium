package com.ulian168.platform.selenium.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.action.ActionStep;
import com.ulian168.platform.selenium.action.ActionStepContainer;
import com.ulian168.platform.selenium.bean.ArrangeBean;
import com.ulian168.platform.selenium.controller.model.CaseDetailVO;
import com.ulian168.platform.selenium.controller.model.TestcaseVO;
import com.ulian168.platform.selenium.service.ArrangeService;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.model.WebActionStepModel;
import com.ulian168.platform.selenium.web.service.WebExecutorService;
import com.ulian168.platform.selenium.web.service.WebUITestCaseReader;
import com.ulian168.platform.selenium.web.task.WebUITestCaseTaskQueue;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@RestController
@ConfigurationProperties
public class WebRestController {
    private static final Logger logger = LoggerFactory.getLogger(WebRestController.class);

    @Resource
    private ProjectConfig projectConfig;
    
    @Resource
    private WebUITestCaseReader reader;
    
    @Resource
    WebExecutorService executorService;
    
    @Resource
    private WebUITestCaseTaskQueue taskQueue;
    
    @Resource
    ActionStepContainer stepContainer;
    @Autowired
    ArrangeService arrangeService;

    @RequestMapping(value = "/testCaseList", produces = "application/json; charset=UTF-8")
    public Object testCaseList() throws Exception {
        File topFile = new File(projectConfig.casePath);
        List<String> list = new ArrayList<String>();
        JSONObject node = getNode(topFile, list);
        logger.info(node.toJSONString());
        return node;
    }
    
    @RequestMapping(value = "/executeTestCase", produces = "application/json; charset=UTF-8")
    public Object executeTestCase(@RequestParam String caseName) {
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        JSONObject json = executorService.execute(testCase);
        logger.info(json.toJSONString());
        return json;
    }
    
    private JSONObject getNode(final File folder, final List<String> list) {
        JSONObject result = new JSONObject();
        JSONArray nodes = new JSONArray();

        result.put("text", folder.getName());
        result.put("nodeId", list.size());
        list.add("");
        result.put("nodes", nodes);
        
        File[] files = folder.listFiles();
        for (File file : files) {
            // 分类，文件直接不考虑
            if (file.isDirectory()) {
                JSONObject to = new JSONObject();
                to.put("text", file.getName());
                to.put("nodeId", list.size());
                list.add("");
                nodes.add(to);
                File[] subFolders = file.listFiles();
                JSONArray caseNodes = new JSONArray();
                to.put("nodes", caseNodes);
                for (File sub : subFolders) {
                    if (sub.isDirectory()) {
                        JSONObject node = getNode(sub, list);
                        caseNodes.add(node);
                    } else {
                        caseNodes.add(loadFromFile(sub, list));
                    }
                }
            } else {
                nodes.add(loadFromFile(file, list));
            }
        }
        return result;
    }
    
    private JSONObject loadFromFile(final File file, final List<String> list) {
        JSONObject caseObject = new JSONObject();
        String readFileToString;
        try {
            readFileToString = FileUtils.readFileToString(file);
            //windows mac换行都处理一下
            readFileToString = readFileToString.split("\r\n")[0];
            readFileToString = readFileToString.split("\n")[0];
            String[] split = readFileToString.split("#");
            caseObject.put("text", split[2].trim());
            caseObject.put("nodeId", list.size());
            list.add("");
            caseObject.put("caseModel", getId(file));
            
            String testcaseDesc = split[2].trim();
            
            //caseObject.put("href", "javascript:Bright.loadPage('showTestcase?caseName=" + getId(file) + "&testcaseDesc=" + testcaseDesc + "')");
            caseObject.put("href", "javascript:Bright.loadPage('editTestCase?caseName=" + getId(file) + "&testcaseDesc=" + testcaseDesc + "')");
        } catch (Exception e) {
            logger.info("filename: {}", file.getAbsoluteFile());
            e.printStackTrace();
        }
        return caseObject;
    }
    
    private String getId(final String fileName) {
        File f = new File(fileName);
        return getId(f);
    }
    
    private String getId(final File file) {
        File baseFile = new File(this.projectConfig.casePath);
        String basicPath = baseFile.getAbsolutePath();
        String thePath = file.getAbsolutePath();
        String idWithSlashs = thePath.substring(basicPath.length());
        //兼容windows
        idWithSlashs = idWithSlashs.replaceAll("\\\\", "_");
        String id = idWithSlashs.replaceAll(WebConstant.SLASH, "_");
        return id;
    }
    
    /**
     * caseName是文件名转换。
     * @param caseName
     * @return
     */
    @RequestMapping("/caseDetail")
    public Object caseDetail(@RequestParam String caseName) {
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        WebActionModel action = reader.load(testCase);
        
        JSONObject result = new JSONObject();
        JSONArray rows = new JSONArray();
        List<WebActionStepModel> steps = action.getSteps();
        
        result.put("current", 1);
        result.put("rowCount", 100);
        result.put("total", steps.size());
        result.put("rows", rows);
        int i = 0;
        for (WebActionStepModel step : steps) {
            JSONObject vo = step.getVO();
            vo.put("id", i++);
            rows.add(vo);
        }
        return result;
    }
    
    /**
     * caseName是文件名转换。
     * @param caseName
     * @return
     */
    @RequestMapping("/delTestcase")
    public Object delTestcase(@RequestParam String caseName) {
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        JSONObject result = new JSONObject();
        File file = new File(testCase);
        if (!file.exists()) {
            String errorMsg = "案例[" + caseName + "]对应文件[" + file.getAbsolutePath() + "]不存在!";
            result.put("retMsg", errorMsg);
            logger.info(errorMsg);
        } else {
            if (file.delete()) {
                result.put("retMsg", "OK");
            } else {
                result.put("retMsg", "删除失败!");
            }
        }
        return result;
    }
    
    @RequestMapping(value={"/saveTestcase"},method={RequestMethod.POST,RequestMethod.GET},produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object saveTestcase(@RequestBody TestcaseVO caseVO) {
        JSONObject result = new JSONObject();
        String caseName = caseVO.getCaseName();
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        BufferedWriter bw = null;
        try {
            File file = new File(testCase);
            if (file.isDirectory()) {
                String fileName = System.currentTimeMillis() + ".txt";
                caseName = caseName + "_" + fileName;
                file = new File(testCase + WebConstant.SLASH + fileName);
            }
            bw = new BufferedWriter(new FileWriter(file));
            //测试用例名
            bw.write("##" + caseVO.getCaseDescName());
            bw.write("\n");
            List<CaseDetailVO> steps = caseVO.getSteps();
            if (steps != null && steps.size() > 0) {
                for (CaseDetailVO step : steps) {
                    String desc = step.getDesc();
                    if (StringUtils.isNotEmpty(desc)) {
                        bw.write("#" + desc);
                        bw.write("\n");
                    }
                    bw.write(step.getRowline());
                    bw.write("\n");
                }
            }
            result.put("retMsg", "OK");
            result.put("caseName", caseName);
            
        } catch (Exception e) {
            result.put("retMsg", "保存失败，原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    @RequestMapping(value={"/saveCaseContent"},method={RequestMethod.POST})
    @ResponseBody
    public Object saveCaseContent(@RequestParam String caseName, @RequestParam String content) {
        JSONObject result = new JSONObject();
        String testCase = caseName.replaceAll("_", WebConstant.SLASH);
        testCase = projectConfig.casePath + testCase;
        BufferedWriter bw = null;
        try {
            File file = new File(testCase);
            if (file.isDirectory()) {
                String fileName = System.currentTimeMillis() + ".txt";
                caseName = caseName + "_" + fileName;
                file = new File(testCase + WebConstant.SLASH + fileName);
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            result.put("retMsg", "OK");
            result.put("caseName", caseName);
            
        } catch (Exception e) {
            result.put("retMsg", "保存失败，原因：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * caseName是文件名转换。
     * @param caseName
     * @return
     */
    @RequestMapping("/metadata")
    public Object getMetadata() {
        JSONObject result = new JSONObject();
        Set<Entry<String, ActionStep>> entrys = stepContainer.entrySet();
        for (Entry<String, ActionStep> entry : entrys) {
            String key = entry.getKey();
            ActionStep value = entry.getValue();
            result.put(key, value.getMetaInf());
        }
        return result;
    }
    
    /**
     * caseName是文件名转换。
     * @param caseName
     * @return
     */
    @RequestMapping(value={"/folders"},method={RequestMethod.POST,RequestMethod.GET},produces = "application/json; charset=UTF-8")
    public Object folders() {
        List<String> list = new ArrayList<String>();
        File base = new File(projectConfig.casePath);
        addFolder(list, base);
        return list;
    }
    
    
    @RequestMapping(value={"/schedules"},method={RequestMethod.POST,RequestMethod.GET},produces = "application/json; charset=UTF-8")
    public Object schedules() {
        List<String> list = taskQueue.getScheduleList();
        JSONObject result = new JSONObject();
        JSONArray rows = new JSONArray();
        
        result.put("current", 1);
        result.put("rowCount", 100);
        result.put("total", list.size());
        result.put("rows", rows);
        for (int i = 0; i< list.size(); i++) {
            JSONObject row = new JSONObject();
            String path = list.get(i);
            row.put("id", i + "");
            row.put("path", path);
            row.put("type", getTestcaseType(path));
            rows.add(row);
        }
        return result;
    }
    
    private String getTestcaseType(final String path) {
        String type = "目录";
        if (path.endsWith(".txt")) {
            type = "案例";
        }
        return type;
    }
    
    @RequestMapping(value={"/arrangeSchedule"},method={RequestMethod.POST})
    @ResponseBody
    public Object arrangeSchedule(@RequestParam String caseName) {
        JSONObject result = new JSONObject();
        try {
            taskQueue.addSchedule(caseName);
            result.put("retMsg", "OK");
            result.put("caseName", caseName);
        } catch (Exception e) {
            result.put("retMsg", "保存失败，原因：" + e.getMessage());
            e.printStackTrace();
        } 
        return result;
    }
    
    @RequestMapping(value={"/delSchedule"},method={RequestMethod.POST})
    @ResponseBody
    public Object delSchedule(@RequestParam String caseName) {
        JSONObject result = new JSONObject();
        try {
            taskQueue.removeSchedule(caseName);
            result.put("retMsg", "OK");
            result.put("caseName", caseName);
        } catch (Exception e) {
            result.put("retMsg", "保存失败，原因：" + e.getMessage());
            e.printStackTrace();
        } 
        return result;
    }
    
    @RequestMapping(value={"/arrange/result"},method={RequestMethod.POST})
    @ResponseBody
    public Object getAll(@RequestBody JSONObject jsonObject) {
    	
    	JSONObject resultJson = new JSONObject();
    	List<ArrangeBean> list = arrangeService.getAll();
    	JSONArray jsonArray = new JSONArray();
    	for(ArrangeBean arrange : list) {
    		JSONObject json = new JSONObject();
    		json.put("createTime", arrange.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss")));
    		json.put("jobId", arrange.getJobId());
    		json.put("serverIp", arrange.getServerIp());
    		json.put("name", arrange.getName());
    		json.put("type", arrange.getType());
    		json.put("state", arrange.getState());
    		json.put("path", arrange.getPath());
    		json.put("reportPath", arrange.getReportPath());
    		jsonArray.add(json);
    	}
    	resultJson.put("result", jsonArray);
    	logger.info("response:"+resultJson.toString());
        return resultJson;
    	//return null;
    }
    
    private void addFolder(final List<String> list, final File folder) {
        if (folder.isDirectory()) {
            String id = getId(folder);
            if (StringUtils.isNotEmpty(id)) {
                list.add(id);
            }
            File[] listFiles = folder.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    list.add(getId(file));
                    File[] subs = file.listFiles();
                    for (File sub : subs) {
                        addFolder(list, sub);
                    }
                }
            }
        }
    }
}
