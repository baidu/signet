/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service;

import com.baidu.stqa.signet.web.bo.Log;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface LogService {
    /**
     * 打印日志，写入日志文件
     * 
     * @param log 日志内容
     * @return
     * 
     */
    public void addLog(Log log);

}
