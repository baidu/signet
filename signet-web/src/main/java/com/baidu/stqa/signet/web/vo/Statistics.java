/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.vo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class Statistics {

    /**
     * storyId
     */
    private int storyId;

    /**
     * 已验收测试点数
     */
    private int testedCaseNum = 0;

    /**
     * 总测试点数
     */
    private int totalCaseNum = 0;

    /**
     * 完成百分比
     */
    private float percent = 0f;

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getTestedCaseNum() {
        return testedCaseNum;
    }

    public void setTestedCaseNum(int testedCaseNum) {
        this.testedCaseNum = testedCaseNum;
    }

    public int getTotalCaseNum() {
        return totalCaseNum;
    }

    public void setTotalCaseNum(int totalCaseNum) {
        this.totalCaseNum = totalCaseNum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

}
