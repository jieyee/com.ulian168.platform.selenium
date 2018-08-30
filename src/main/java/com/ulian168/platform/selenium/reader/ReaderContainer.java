package com.ulian168.platform.selenium.reader;

import java.util.HashMap;

import org.springframework.stereotype.Component;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
@Component
public class ReaderContainer extends HashMap<String, CaseReader>{
    private static final long serialVersionUID = 1L;

    public void putReader(final String key, final CaseReader value) {
        this.put(key, value);
    }
    
    public CaseReader getReader(final String key) {
        CaseReader caseReader = this.get(key);
        return caseReader;
    }
}
