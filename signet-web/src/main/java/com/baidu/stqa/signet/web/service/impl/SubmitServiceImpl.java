/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Submit;
import com.baidu.stqa.signet.web.mapper.SubmitMapper;
import com.baidu.stqa.signet.web.service.SubmitService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("submitService")
public class SubmitServiceImpl implements SubmitService {

    @Autowired
    private SubmitMapper submitMapper;

    @Override
    public Submit querySubmit(Long projectId, Long storyId) {
        Submit submit = new Submit();
        submit.setProjectId(projectId);
        submit.setStoryId(storyId);
        List<Submit> submits = submitMapper.select(submit);
        if (submits.isEmpty()) {
            return null;
        } else {
            return submits.get(0);
        }
    }

    @Override
    public boolean submitStory(Long projectId, Long storyId) {
        Submit submit = new Submit();
        submit.setProjectId(projectId);
        submit.setStoryId(storyId);
        submitMapper.insert(submit);
        return true;
    }

    @Override
    public void cancelSubmitStory(Long projectId, Long storyId) {
        Submit submit = new Submit();
        submit.setProjectId(projectId);
        submit.setStoryId(storyId);
        submitMapper.delete(submit);
    }

    @Override
    public List<Submit> querySubmit(Long projectId) {
        Submit submit = new Submit();
        submit.setProjectId(projectId);
        return submitMapper.select(submit);
    }

}
