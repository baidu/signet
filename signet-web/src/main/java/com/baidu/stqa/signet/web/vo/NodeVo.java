/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.vo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class NodeVo {

    public String text = "";
    
    public String roleId = "";
    
    public Long id;
    
    public Long seq;
    
    public Integer remarkFlag;
    
    public Integer isRetest;

    public Integer getIsRetest() {
        return isRetest;
    }

    public void setIsRetest(Integer isRetest) {
        this.isRetest = isRetest;
    }

    public NodeVo[] children = null;

    public Integer getRemarkFlag() {
        return remarkFlag;
    }

    public void setRemarkFlag(Integer remarkFlag) {
        this.remarkFlag = remarkFlag;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NodeVo[] getChildren() {
        return children;
    }

    public void setChildren(NodeVo[] children) {
        this.children = children;
    }

}
