/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.bo.Remark;
import com.baidu.stqa.signet.web.mapper.NodeMapper;
import com.baidu.stqa.signet.web.mapper.RemarkMapper;
import com.baidu.stqa.signet.web.service.RemarkService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("remarkService")
public class RemarkServiceImpl implements RemarkService {

    @Autowired
    private RemarkMapper remarkMapper;

    @Autowired
    private NodeMapper nodeMapper;

    public Long addRemark(Long nodeId, String content, String user) {
        Remark remark = new Remark();
        remark.setContent(content);
        remark.setNodeId(nodeId);
        remark.setUser(user);
        remark.setAddDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        remarkMapper.insert(remark);
        changeRemarkFlag(nodeId);
        return remark.getId();
    }

    @Override
    public void removeRemark(Long nodeId, Long id) {
        remarkMapper.delete(id);
        changeRemarkFlag(nodeId);

    }

    @Override
    public List<Remark> getRemarksByNodeId(Long nodeId) {
        return remarkMapper.selectByNodeId(nodeId);
    }

    private void changeRemarkFlag(Long nodeId) {
        Node node = new Node();
        node.setNodeId(nodeId);
        List<Remark> list = getRemarksByNodeId(nodeId);
        if (list.isEmpty()) {
            node.setRemarkFlag(0);
        } else {
            node.setRemarkFlag(1);
        }
        nodeMapper.update(node);
    }

}
