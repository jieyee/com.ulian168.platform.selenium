package com.ulian168.platform.selenium.web.model;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ulian168.platform.selenium.web.action.step.WebActionStep;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public class WebActionStepModel {
    //测试用例的名字。##开始，第一行.
    private String caseName;
    private String remark;
    private String fullDescription;
    private String opName;
    private String opParams;
    private WebActionStep webActionStep;
    
    public WebActionStepModel(String fullDescription, String caseName, String remark, String opName, String opParams) {
        super();
        this.fullDescription = fullDescription;
        this.caseName = caseName;
        this.remark = remark;
        this.opName = opName;
        this.opParams = opParams;
    }
    
    /**
     * @return the fullDescription
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * @param fullDescription the fullDescription to set
     */
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    /**
     * @return the opName
     */
    public String getOpName() {
        return opName;
    }
    /**
     * @param opName the opName to set
     */
    public void setOpName(String opName) {
        this.opName = opName;
    }
    /**
     * @return the opParams
     */
    public String getOpParams() {
        return opParams;
    }
    /**
     * @param opParams the opParams to set
     */
    public void setOpParams(String opParams) {
        this.opParams = opParams;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        if (StringUtils.isEmpty(remark)) {
            return this.getOpName() + "|"+ UUID.randomUUID().toString();
        }
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * @return the webActionStep
     */
    public WebActionStep getWebActionStep() {
        return webActionStep;
    }
    
    /**
     * @param webActionStep the webActionStep to set
     */
    public void setWebActionStep(WebActionStep webActionStep) {
        this.webActionStep = webActionStep;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    
    @JSONField(serialize=false)  
    public JSONObject getVO() {
        JSONObject vo = new JSONObject();
        vo.put("name", this.opName);
        vo.put("desc", this.remark);
        
        //部分步骤没有参数.
        if (StringUtils.isNotEmpty(opParams)) {
            String[] params = opParams.split("\\s");
            String[] tv = getTypeValue(params[0]);
            vo.put("paramType1", tv[0]);
            vo.put("param1", tv[1]);
            if (params.length == 2) {
                String[] tv2 = getTypeValue(params[1]);
                vo.put("paramType2", tv2[0]);
                vo.put("param2", tv2[1]);
            }
        }
        return vo;
    }
    
    private String[] getTypeValue(final String param) {
        String[] tv = new String[2];
        if (param.startsWith("[")) {
            tv[0] = "变量";
            tv[1] = param.substring(1, param.length() - 1);
        } else {
            tv[0] = "常量";
            String v = param;
            if (v.startsWith("\"")) {
                v = StringUtils.substringBetween(v, "\"",  "\"");
            }
            tv[1] = v;
        }
        return tv;
    }
}
