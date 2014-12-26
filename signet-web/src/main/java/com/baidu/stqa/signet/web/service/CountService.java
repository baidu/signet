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

import com.baidu.stqa.signet.web.vo.Count;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface CountService {
    /**
     * 获得历史分项目访问量
     * 
     * @return
     */
    public List<Count> countAllVisit();

    /**
     * 获得最近时间段内分项目访问量
     * 
     * @param date
     * @return
     */
    public List<Count> countNewlyVisit(String date);

    /**
     * 获得每日访问量
     * 
     * @return
     */
    public List<Count> countAllLine();
}
