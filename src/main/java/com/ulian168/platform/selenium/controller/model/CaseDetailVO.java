package com.ulian168.platform.selenium.controller.model;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * 自动化冒烟平台.<br>
 * ActionStep bootgrid描述vo.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public class CaseDetailVO {
    private String id;
    private String name;
    private String paramType1;
    private String param1;
    private String paramType2;
    private String param2;
    private String desc;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the paramType1
     */
    public String getParamType1() {
        return paramType1;
    }
    /**
     * @param paramType1 the paramType1 to set
     */
    public void setParamType1(String paramType1) {
        this.paramType1 = paramType1;
    }
    /**
     * @return the param1
     */
    public String getParam1() {
        return param1;
    }
    /**
     * @param param1 the param1 to set
     */
    public void setParam1(String param1) {
        this.param1 = param1;
    }
    /**
     * @return the paramType2
     */
    public String getParamType2() {
        return paramType2;
    }
    /**
     * @param paramType2 the paramType2 to set
     */
    public void setParamType2(String paramType2) {
        this.paramType2 = paramType2;
    }
    /**
     * @return the param2
     */
    public String getParam2() {
        return param2;
    }
    /**
     * @param param2 the param2 to set
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    @JSONField(serialize=false) 
    public String getRowline() {
        StringBuilder line = new StringBuilder();
        line.append(name);
        this.operateLine(paramType1, param1, line);
        this.operateLine(paramType2, param2, line);
        return line.toString();
    }
    
    private void operateLine(String p, String v, StringBuilder sb) {
        if (StringUtils.isNotEmpty(p)) {
            if (WebConstant.VO_CON.equals(p)) {
                sb.append(" ").append(v);
            }
            if (WebConstant.VO_VAR.equals(p)) {
                sb.append(" [").append(v).append("]");
            }
        }
    }
}
