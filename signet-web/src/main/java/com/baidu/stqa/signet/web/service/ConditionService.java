/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface ConditionService {

    /**
     * 保存用户查询条件
     * 
     * @param user
     * @param projectId
     * @param conditions
     * @return
     */
    public boolean saveUserCondition(String user, Long projectId, String conditions);

    /**
     * 增加用户查询条件
     * 
     * @param user
     * @param projectId
     * @param conditions
     * @return
     */
    public boolean addUserCondition(String user, Long projectId, String conditions);

    /**
     * 查询用户查询条件
     * 
     * @param user
     * @param projectId
     * @return
     */
    public String queryUserCondition(String user, Long projectId);
}
