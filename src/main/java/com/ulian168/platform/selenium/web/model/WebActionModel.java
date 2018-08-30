package com.ulian168.platform.selenium.web.model;

import java.util.List;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public class WebActionModel {
    private List<WebActionStepModel> steps;
    /**
     * 任务id
     */
    private String jobId;
    /**
     * 源路径
     */
    private String root;
    /**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
     * @return the steps
     */
    public List<WebActionStepModel> getSteps() {
        return steps;
    }

    /**
     * @param steps the steps to set
     */
    public void setSteps(List<WebActionStepModel> steps) {
        this.steps = steps;
    }
    
    public String getTestCaseName() {
        if (steps != null && steps.get(0) != null) {
           return steps.get(0).getCaseName();
        }
        return null;
    }
}
