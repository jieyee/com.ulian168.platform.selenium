package com.ulian168.platform.selenium.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化冒烟平台.
 * 
 * @author 周明
 * @since 2017-12-09
 */
public class TreeNode {

    private String id;
    private String pid;
    private String name;
    private Boolean open;
    private String url;
    private List<TreeNode> children;
    private Boolean hasChild;

    public TreeNode(String id, String pid, String name, Boolean open, String url) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.open = open;
        this.url = url;
        this.hasChild=false;
        this.children = new ArrayList<TreeNode>(0);
    }

    public TreeNode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }
}
