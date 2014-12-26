/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.extend.service;

import java.util.List;

import com.baidu.stqa.signet.extend.bo.Story;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface StoryService {

    /**
     * 获得story列表
     * 
     * @param projectId
     * @param projectName
     * @param conditions
     * @return
     */
    public List<Story> listStory(Long projectId, String projectName, Object conditions);

    /**
     * 获得Story信息
     * 
     * @param projectId
     * @param projectName
     * @param storyId
     * @return
     */
    public Story queryStoryInfo(Long projectId, String projectName, Long storyId);
}
