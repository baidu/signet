/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Condition;
import com.baidu.stqa.signet.web.mapper.UserConditionMapper;
import com.baidu.stqa.signet.web.service.ConditionService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("conditionSevice")
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    private UserConditionMapper userConditionMapper;

    @Override
    public boolean saveUserCondition(String user, Long projectId, String conditions) {
        if (queryUserCondition(user, projectId) == null) {
            addUserCondition(user, projectId, conditions);
        } else {
            Condition condition = new Condition(user, projectId, conditions);
            userConditionMapper.update(condition);
        }
        return true;
    }

    @Override
    public boolean addUserCondition(String user, Long projectId, String conditions) {
        Condition condition = new Condition(user, projectId, conditions);
        userConditionMapper.insert(condition);
        return true;
    }

    @Override
    public String queryUserCondition(String user, Long projectId) {
        Condition condition = new Condition(user, projectId, null);
        Condition resultCondition = userConditionMapper.select(condition);
        if (resultCondition == null) {
            return null;
        } else {
            return resultCondition.getConditions();
        }
    }

}
