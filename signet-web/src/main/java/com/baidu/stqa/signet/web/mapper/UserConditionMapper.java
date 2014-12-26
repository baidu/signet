/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.mapper;

import com.baidu.stqa.signet.web.bo.Condition;
import com.baidu.stqa.signet.web.common.mybatis.MybatisMapper;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@MybatisMapper
public interface UserConditionMapper {

    public void update(Condition condition);

    public void insert(Condition condition);

    public Condition select(Condition condition);

}
