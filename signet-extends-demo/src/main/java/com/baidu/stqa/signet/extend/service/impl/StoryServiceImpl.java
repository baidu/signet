/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.extend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baidu.stqa.signet.extend.bo.Story;
import com.baidu.stqa.signet.extend.service.StoryService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class StoryServiceImpl implements StoryService {

    public List<Story> listStory(Long projectId, String projectName, Object conditions) {
        List<Story> storyList = new ArrayList<Story>();
        for (long i = 1; i <= 10; i++) {
            Story story = new Story();
            story.setTitle("story" + i);
            story.setId(i);
            story.setStatus("0");
            story.setType("story");
            storyList.add(story);
        }
        return storyList;
    }

    public Story queryStoryInfo(Long projectId, String projectName, Long storyId) {
        Story story = new Story();
        story.setTitle("story1");
        story.setId(1L);
        story.setStatus("0");
        story.setType("story");
        story.setDetail("这是story详情");
        return story;
    }
}
