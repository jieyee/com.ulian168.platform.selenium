package com.ulian168.platform.selenium.action;

import com.alibaba.fastjson.JSONObject;
import com.ulian168.platform.selenium.web.context.WebContext;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public interface ActionStep{
    
    int execute(WebContext webContext);
    
    String getName();
    
    JSONObject getMetaInf();
}
