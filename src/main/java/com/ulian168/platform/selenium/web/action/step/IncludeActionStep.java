/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.web.action.step;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;
import com.ulian168.platform.selenium.web.util.WebConstant;

/**
 * @Title:IncludeActionStep
 * @Description:引用模块功能
 * @author liuming
 * @date 2018年8月15日 下午2:57:37
 * @version V1.0
 */
@Component(WebConstant.ACTIONSTEP_INCLUDE)
public class IncludeActionStep extends WebActionStep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int execute(WebContext webContext) {
		return 0;
	}

	@Override
	public String getName() {
		return WebConstant.ACTIONSTEP_INCLUDE;
	}

	@Override
	public JSONObject getMetaInf() {
		JSONObject metaInf = new JSONObject();
        metaInf.put("name", this.getName());
        metaInf.put("paramType1", WebConstant.VO_CON);
        return metaInf;
	}

}
