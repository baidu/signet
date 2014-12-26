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
import java.util.Map;

import com.baidu.stqa.signet.web.vo.Statistics;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface StatisticsService {

    /**
     * 统计测试进度
     * 
     * @param storyIds
     * @param projectId
     */
    public Map<Long, Statistics> queryTestProgress(List<Long> storyIds, Long projectId);

    /**
     * 获得系统统计
     * 
     * @param projectId
     * @param ids
     * @return
     */
    public Map<String, Object> queryStatistics(List<Long> stroyIds, Long projectId);
}
