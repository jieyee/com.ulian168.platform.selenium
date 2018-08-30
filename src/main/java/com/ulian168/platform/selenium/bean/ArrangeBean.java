/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.bean;

import java.time.LocalDateTime;

/**
 * @Title:ArrangeBean
 * @Description:TODO
 * @author liuming
 * @date 2018年8月9日 下午3:23:06
 * @version V1.0
 */
public class ArrangeBean {
	/**
	 * 任务编号
	 */
    private String jobId;
	/**
	 * 执行任务服务器ip
	 */
	private String serverIp;
	/**
	 * 名称.
	 */
	private String name;
	/**
	 * 地址.
	 */
	private String path;
	/**
	 * 类型.
	 */
	private String type;
	/**
	 * 状态.
	 */
	private String state;
	/**
	 * 创建时间.
	 */
	private LocalDateTime createTime;
	/**
	 * 报告下载地址.
	 */
	private String reportpath;

	/**
	 * @return the createTime
	 */
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the reportPath
	 */
	public String getReportPath() {
		return reportpath;
	}

	/**
	 * @param reportPath
	 *            the reportPath to set
	 */
	public void setReportPath(String reportPath) {
		this.reportpath = reportPath;
	}

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}

	/**
	 * @param serverIp
	 *            the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @return the reportpath
	 */
	public String getReportpath() {
		return reportpath;
	}

	/**
	 * @param reportpath
	 *            the reportpath to set
	 */
	public void setReportpath(String reportpath) {
		this.reportpath = reportpath;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
