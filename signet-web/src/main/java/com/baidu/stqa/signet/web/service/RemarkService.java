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

import com.baidu.stqa.signet.web.bo.Remark;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface RemarkService {
    /**
     * 添加评论
     * 
     * @param nodeId
     * @param content
     * @param user
     * @return
     */
    public Long addRemark(Long nodeId, String content, String user);

    /**
     * 删除评论
     * 
     * @param nodeId
     * @param id
     */
    public void removeRemark(Long nodeId, Long id);

    /**
     * 获得验收点内所有评论
     * 
     * @param nodeId
     * @return
     */
    public List<Remark> getRemarksByNodeId(Long nodeId);

}
