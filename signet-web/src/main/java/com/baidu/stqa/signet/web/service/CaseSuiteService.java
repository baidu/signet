package com.baidu.stqa.signet.web.service;

import com.baidu.stqa.signet.web.bo.EditStatus;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public interface CaseSuiteService {

    /**
     * Story是否可编辑
     * 
     * @param projectId
     * @param storyId
     * @return
     */
    public boolean isCardLocked(Long projectId, Long storyId);

    /**
     * 延长占用时长
     * 
     * @param projectId
     * @param storyId
     * @param editUserName
     * @return
     */
    public EditStatus renewCardEditLock(Long projectId, Long storyId, String editUserName);

    /**
     * 释放编辑权限
     * 
     * @param projectId
     * @param storyId
     * @param editUserName
     * @return
     */
    public boolean releaseCardEditLock(Long projectId, Long storyId, String editUserName);

    /**
     * 申请编辑权限
     * 
     * @param projectId
     * @param storyId
     * @param editUserName
     * @param title
     * @return
     */
    public EditStatus ApplyForEdit(Long projectId, Long storyId, String editUserName, String title);

}