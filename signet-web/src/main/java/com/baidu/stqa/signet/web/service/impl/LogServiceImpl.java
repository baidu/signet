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

import com.baidu.stqa.signet.web.bo.Log;
import com.baidu.stqa.signet.web.mapper.LogMapper;
import com.baidu.stqa.signet.web.service.LogService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.insert(log);
    }

}
