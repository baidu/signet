/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.stqa.signet.web.bo.Card;
import com.baidu.stqa.signet.web.bo.EditStatus;
import com.baidu.stqa.signet.web.mapper.CardMapper;
import com.baidu.stqa.signet.web.service.CaseSuiteService;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Service("caseSuiteService")
public class CaseSuiteServiceImpl implements CaseSuiteService {

    // 分配给用户的初始编辑时间
    private static final int N = 10;

    @Autowired
    private CardMapper cardMapper;

    @Override
    public boolean isCardLocked(Long projectId, Long storyId) {
        Card card = queryCard(projectId, storyId);
        if (card.getIsEdit().intValue() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean releaseCardEditLock(Long projectId, Long storyId, String editUserName) {
        Card card = queryCard(projectId, storyId);
        if (editUserName == null || !editUserName.equals(card.getEditUserName())) {
            return false;
        } else {
            card.setStartEditTime(null);
            card.setEditUserName(editUserName);
            card.setIsEdit(0);
            cardMapper.update(card);
            return true;
        }

    }

    @Override
    public EditStatus renewCardEditLock(Long projectId, Long storyId, String editUserName) {
        Card card = queryCard(projectId, storyId);
        Date now = new Date();
        EditStatus es = new EditStatus();

        card.setIsEdit(1);
        card.setStartEditTime(now);

        if (editUserName == null || !editUserName.equals(card.getEditUserName())) {
            es.setCardCanEdit(false);
            es.setEditUserName(card.getEditUserName());
            return es;
        } else {
            es.setCardCanEdit(true);
            es.setEditUserName(card.getEditUserName());
            card.setEditUserName(editUserName);
            cardMapper.update(card);
            return es;
        }
    }

    public EditStatus ApplyForEdit(Long projectId, Long storyId, String editUserName, String title) {
        Card card = queryCard(projectId, storyId);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, -N);
        Date timeflag = calendar.getTime();
        EditStatus es = new EditStatus();
        // 如果卡片不存在，则插入
        if (editUserName == null) {
            es.setCardCanEdit(false);
            es.setEditUserName(editUserName);
            return es;
        }
        if (card == null) {
            card = new Card();
            card.setCardId(storyId);
            card.setProjectId(projectId);
            card.setIsEdit(1);
            card.setEditUserName(editUserName);
            card.setStartEditTime(now);
            card.setTitle(title);
            cardMapper.insert(card);
            es.setCardCanEdit(true);
            es.setEditUserName(editUserName);
            return es;
        } else {

            boolean flag = false;// 标识是否可编辑

            // 如果同一个用户申请编辑这张卡片，允许编辑，刷新数据库,不支持登陆时，editUserName默认传”-1“
            if (editUserName != null && editUserName.equals(card.getEditUserName())) {
                flag = true;
            }
            // 如果卡片未被编辑，则允许编辑，刷新数据库
            else if (card.getIsEdit().intValue() == 0) {
                flag = true;
            } else if (card.getStartEditTime() == null || card.getStartEditTime().before(timeflag)) {
                // 如果卡片正在被编辑，且开始编辑时间比当前时间早n分钟，则允许编辑，刷新数据库
                flag = true;
            } else {// 不可编辑
                flag = false;
            }

            if (flag == true) {
                es.setCardCanEdit(true);
                es.setEditUserName(editUserName);
                card.setIsEdit(1);
                card.setEditUserName(editUserName);
                card.setStartEditTime(now);
                cardMapper.update(card);
            } else {
                es.setCardCanEdit(false);
                es.setEditUserName(card.getEditUserName());
            }
            return es;
        }
    }

    private Card queryCard(Long projectId, Long storyId) {
        Card card = new Card();
        card.setProjectId(projectId);
        card.setCardId(storyId);
        return cardMapper.queryByProjectIdAndStoryId(card);

    }
}