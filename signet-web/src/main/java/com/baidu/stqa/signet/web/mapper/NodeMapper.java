/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.mapper;

import java.util.List;

import com.baidu.stqa.signet.web.bo.Node;
import com.baidu.stqa.signet.web.common.mybatis.MybatisMapper;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@MybatisMapper
public interface NodeMapper {

    public List<Node> selectByProjectId(Long projectId);

    public List<Node> selectByCondi(Node node);

    public void insert(Node node);

    public List<Node> selectByParentId(Long parentId);

    public void update(Node node);

    public Node selectById(Long nodeId);

    public List<Node> selectByIds(List<Long> nodeIds);

    public void deleteBatch(Long[] ids);
    
    public void delete(Node node);
}
