/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.bo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class Node {

    private Long nodeId;

    private Long parentId;

    private String roleSignId;

    private Boolean isCare;

    private Long storyId;

    private Long projectId;

    private String nodeText;

    private Long seq;

    private String createPerson;

    private Integer remarkFlag;

    private Integer isRetest;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRoleSignId() {
        return roleSignId;
    }

    public void setRoleSignId(String roleSignId) {
        this.roleSignId = roleSignId;
    }

    public Boolean getIsCare() {
        return isCare;
    }

    public void setIsCare(Boolean isCare) {
        this.isCare = isCare;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getNodeText() {
        return nodeText;
    }

    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Integer getRemarkFlag() {
        return remarkFlag;
    }

    public void setRemarkFlag(Integer remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public Integer getIsRetest() {
        return isRetest;
    }

    public void setIsRetest(Integer isRetest) {
        this.isRetest = isRetest;
    }

}