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

public class Condition {

    private Long projectId;

    private String user;

    private String conditions;

    public Condition() {

    }

    public Condition(String user, Long projectId, String conditions) {
        this.user = user;
        this.projectId = projectId;
        this.conditions = conditions;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

}
