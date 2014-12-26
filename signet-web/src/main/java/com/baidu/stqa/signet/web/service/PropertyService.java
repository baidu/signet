/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service;

import java.util.Map;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface PropertyService {

    /**
     * 获得项目所有配置
     * 
     * @param projectId
     * @return
     */
    public Map<String, Object> queryProperty(Long projectId);

    /**
     * 查询项目配置
     * 
     * @param projectId
     * @param property
     * @return
     */
    public String queryProperty(Long projectId, String property);

    /**
     * 设置选择模式（单选or多选）
     * 
     * @param projectId 空间id flag 0单选，1多选
     * @return
     * 
     */
    public void saveRoleMode(Long projectId, String flag);

    /**
     * 保存添加bug路径
     * 
     * @param projectId
     * @param bugPath
     */
    public void saveBugPath(Long projectId, String bugPath);

}
