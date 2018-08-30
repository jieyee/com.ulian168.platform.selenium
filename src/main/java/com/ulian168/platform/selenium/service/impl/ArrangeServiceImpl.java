/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulian168.platform.selenium.bean.ArrangeBean;
import com.ulian168.platform.selenium.mapper.ArrangeMapper;
import com.ulian168.platform.selenium.service.ArrangeService;

/**
 * @Title:ArrangeServiceImpl
 * @Description:TODO
 * @author liuming
 * @date 2018年8月9日 下午4:23:45
 * @version V1.0
 */
@Service
public class ArrangeServiceImpl implements ArrangeService {

	@Autowired
	ArrangeMapper arrangeMapper;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	@Override
	public List<ArrangeBean> getAll() {
		// TODO Auto-generated method stub
		return arrangeMapper.getAll();
	}
	/*@Override
	public int insert(ArrangeBean bean) {
		// TODO Auto-generated method stub
		arrangeMapper.add(bean);
		return 0;
	}*/
	@Override
	public int insert(ArrangeBean bean) {
		// TODO Auto-generated method stub
		arrangeMapper.add(bean);
		return 0;
	}

}
