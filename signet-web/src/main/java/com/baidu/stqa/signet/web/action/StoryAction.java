/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.extend.bo.Story;
import com.baidu.stqa.signet.extend.service.StoryService;
import com.baidu.stqa.signet.extend.service.impl.StoryServiceImpl;
import com.baidu.stqa.signet.web.service.ConditionService;
import com.baidu.stqa.signet.web.service.StatisticsService;
import com.baidu.stqa.signet.web.vo.Statistics;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("storyAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryAction extends BaseAction{
	
	private StoryService storyService = new StoryServiceImpl();
	
	@Autowired
	private ConditionService conditionService;
	
	@Autowired
	private StatisticsService statisticsService;
	

	/**
	 * 获得story列表
	 * @param projectId
	 * @param projectName
	 * @param conditions
	 * @param searchFlag
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/project/{projectId}/story", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> queryStory(@PathVariable long projectId, String projectName, String conditions, boolean searchFlag) throws UnsupportedEncodingException {
		doLog(projectId);
		String user = getUser();
		if(!searchFlag){
			conditions = conditionService.queryUserCondition(user, projectId);
		}else{
			conditionService.saveUserCondition(user, projectId, URLDecoder.decode(conditions,"utf-8"));
		}
		
		List<Story> storyList = storyService.listStory(projectId, projectName, conditions);
		
		List<Long> ids = new ArrayList<Long>();
		for(Story story : storyList){
			ids.add(story.getId());
		}
		
		Map<Long,Statistics> statisticsMap = statisticsService.queryTestProgress(ids, projectId);
		
		for(Story story : storyList){
			story.setTestedCaseNum(statisticsMap.get(story.getId()).getTestedCaseNum());
			story.setTotalCaseNum(statisticsMap.get(story.getId()).getTotalCaseNum());
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("storyList", storyList);
		resultMap.put("conditions", conditions);
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 获得story详情
	 * @param projectId
	 * @param storyId
	 * @param projectName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/project/{projectId}/story/{storyId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Story> queryStoryDetail(@PathVariable long projectId, @PathVariable long storyId, String projectName) throws UnsupportedEncodingException {
		doLog(projectId);
		Story story = storyService.queryStoryInfo(projectId, projectName, storyId);
		return new ResponseEntity<Story>(story, HttpStatus.OK);
	}
}
