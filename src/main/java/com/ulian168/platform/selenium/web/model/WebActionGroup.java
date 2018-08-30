package com.ulian168.platform.selenium.web.model;

import java.util.List;

public class WebActionGroup {
    
    private String groupId;
    
    private String groupDesc;
    
    private List<WebActionModel> list;

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the groupDesc
     */
    public String getGroupDesc() {
        return groupDesc;
    }

    /**
     * @param groupDesc the groupDesc to set
     */
    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    /**
     * @return the list
     */
    public List<WebActionModel> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<WebActionModel> list) {
        this.list = list;
    }
}
