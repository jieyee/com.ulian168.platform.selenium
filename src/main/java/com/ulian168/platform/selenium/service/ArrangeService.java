/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.service;

import java.util.List;

import com.ulian168.platform.selenium.bean.ArrangeBean;

/**
 * @Title:ArrangeService
 * @Description:TODO
 * @author liuming
 * @date 2018年8月9日 下午4:23:19
 * @version V1.0
 */
public interface ArrangeService {
	public List<ArrangeBean> getAll();
	public int insert(ArrangeBean bean);
}
