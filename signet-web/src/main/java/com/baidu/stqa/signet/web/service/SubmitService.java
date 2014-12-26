/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service;

import java.util.List;

import com.baidu.stqa.signet.web.bo.Submit;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface SubmitService {
    /**
     * 查询提测状态
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    public Submit querySubmit(Long projectId, Long storyId);

    /**
     * 执行提测
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    public boolean submitStory(Long projectId, Long storyId);

    /**
     * 查询项目全部story提测状态
     * 
     * @param projectId
     * @return
     */
    public List<Submit> querySubmit(Long projectId);

    /**
     * 取消提测
     * 
     * @param projectId
     * @param storyId
     */
    public void cancelSubmitStory(Long projectId, Long storyId);
}
