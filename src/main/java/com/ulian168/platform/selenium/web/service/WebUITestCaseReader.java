package com.ulian168.platform.selenium.web.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ulian168.platform.selenium.reader.CaseReader;
import com.ulian168.platform.selenium.reader.ReaderContainer;
import com.ulian168.platform.selenium.web.model.WebActionModel;
import com.ulian168.platform.selenium.web.model.WebActionStepModel;
import com.ulian168.platform.selenium.web.util.ProjectConfig;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component(WebConstant.READER_WEB)
public class WebUITestCaseReader implements CaseReader {
    private static final Logger logger = LoggerFactory.getLogger(WebUITestCaseReader.class);

    @Resource
    private ReaderContainer readerContainer;
    
    @Resource
    private ProjectConfig projectConfig;

    @PostConstruct
    private void putContainer() {
        readerContainer.put(WebConstant.READER_WEB, this);
    }

    public WebActionModel load(final File file) {
        logger.info("加载testcase:{}", file);
        WebActionModel actionModel = new WebActionModel();
        BufferedReader br = null;
        String remark = null;
        String caseName = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmp = null;

            List<WebActionStepModel> steps = new ArrayList<WebActionStepModel>();
            actionModel.setSteps(steps);
            while ((tmp = br.readLine()) != null) {
                tmp = tmp.trim();
                //剔除空行
                if (StringUtils.isEmpty(tmp)) {
                    continue;
                }
                if (tmp.startsWith("##")) {
                    caseName = tmp.substring(2);
                } else if (tmp.startsWith("#")) {
//                    if (StringUtils.isEmpty(remark)) {
//                        remark = tmp;
//                    } else {
//                        remark = remark + "\n" + tmp;
//                    }
                    remark = tmp.substring(1);
                } else {
                    String desc = tmp;
                    String[] row = tmp.split("\\s");
                    String opName = row[0];
                    String opParams = tmp.substring(opName.length()).trim();
                   
                    if (StringUtils.isNotEmpty(remark)) {
                        desc = "[" + remark + "]" + desc;
                    }
                    WebActionStepModel stepModel = new WebActionStepModel(desc, caseName, remark, opName, opParams);
                    steps.add(stepModel);
                    remark = null;
                    caseName = null;
                    logger.info("加载测试用例步骤:{}", stepModel);
                }
            }
        } catch (Exception e) {
            logger.error("案例[{}]加载失败", caseName);
        } finally {
            IOUtils.closeQuietly(br);
        }
        return actionModel;
    }
    
    public WebActionModel excuteLoad(final File file) {
        logger.info("加载testcase:{}", file);
        WebActionModel actionModel = new WebActionModel();
        BufferedReader br = null;
        String remark = null;
        String caseName = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String tmp = null;

            List<WebActionStepModel> steps = new ArrayList<WebActionStepModel>();
            actionModel.setSteps(steps);
            //增加执行前置事件步骤
            WebActionStepModel beforeStepModel = new WebActionStepModel("执行前置事件", caseName, remark, WebConstant.ACTIONSTEP_EVENT_BEFORE, "");
            steps.add(beforeStepModel);
            while ((tmp = br.readLine()) != null) {
                tmp = tmp.trim();
                //剔除空行
                if (StringUtils.isEmpty(tmp)) {
                    continue;
                }
                if (tmp.startsWith("##")) {
                    caseName = tmp.substring(2);
                } else if (tmp.startsWith("#")) {
//                    if (StringUtils.isEmpty(remark)) {
//                        remark = tmp;
//                    } else {
//                        remark = remark + "\n" + tmp;
//                    }
                    remark = tmp.substring(1);
                } else {
                    String desc = tmp;
                    String[] row = tmp.split("\\s");
                    String opName = row[0];
                    String opParams = tmp.substring(opName.length()).trim();
                    //插入模块引用
                    if (opName.equalsIgnoreCase(WebConstant.ACTIONSTEP_INCLUDE)) {
                    	 String path = projectConfig.casePath + opParams;
                    	 File includeFile = new File(path);
                    	 WebActionModel includeModel = this.load(includeFile);
                    	 includeModel.getSteps().get(0).setCaseName(caseName);
                    	 steps.addAll(includeModel.getSteps());
                    	 continue;
                    }
                    if (StringUtils.isNotEmpty(remark)) {
                        desc = "[" + remark + "]" + desc;
                    }
                    WebActionStepModel stepModel = new WebActionStepModel(desc, caseName, remark, opName, opParams);
                    steps.add(stepModel);
                    remark = null;
                    caseName = null;
                    logger.info("加载测试用例步骤:{}", stepModel);
                }
            }
        } catch (Exception e) {
            logger.error("案例[{}]加载失败", caseName);
        } finally {
            IOUtils.closeQuietly(br);
        }
        return actionModel;
    }

    public WebActionModel load(final String path) {
        File file = new File(path);
        return load(file);
    }
    
    public WebActionModel excuteLoad(final String path) {
        File file = new File(path);
        return excuteLoad(file);
    }
    
    public Map<String, Object> loadFolder(final File folder) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (folder.isDirectory()) {
            resultMap.put("folder", folder);
            
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            resultMap.put("nodes", list);
            
            File[] listFiles = folder.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    Map<String, Object> subMap = loadFolder(file.getAbsolutePath());
                    list.add(subMap);
                } else {
                    WebActionModel model = load(file);
                    Map<String, Object> modelMap = new HashMap<String, Object>();
                    modelMap.put("text", model);
                    list.add(modelMap);
                }
            }
        } else {
            throw new Exception(folder.getAbsolutePath() + "不是目录.");
        }
        return resultMap;
    }

    public Map<String, Object> loadFolder(final String folderName) throws Exception {
        File folder = new File(folderName);
        return loadFolder(folder);
    }
}
