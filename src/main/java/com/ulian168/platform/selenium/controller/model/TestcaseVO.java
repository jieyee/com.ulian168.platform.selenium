/**
 * 
 */
package com.ulian168.platform.selenium.controller.model;

import java.util.List;

/**
 * 自动化冒烟平台.<br>
 * steps的opName，params目前是合并提交的，以后可考虑细化.<br>
 * caseName是文件名转换<br>
 * caseDescName文件中第一行case用例名.<br>
 * @author 周明
 * @since 2017-12-09
 */
public class TestcaseVO {
    private String caseName;
    private String caseDescName;
    private List<CaseDetailVO> steps;
    
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
     * @return the caseDescName
     */
    public String getCaseDescName() {
        return caseDescName;
    }
    /**
     * @param caseDescName the caseDescName to set
     */
    public void setCaseDescName(String caseDescName) {
        this.caseDescName = caseDescName;
    }
    /**
     * @return the steps
     */
    public List<CaseDetailVO> getSteps() {
        return steps;
    }
    /**
     * @param steps the steps to set
     */
    public void setSteps(List<CaseDetailVO> steps) {
        this.steps = steps;
    }
}
