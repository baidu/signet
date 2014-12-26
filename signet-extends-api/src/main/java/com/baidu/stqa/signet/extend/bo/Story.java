/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.extend.bo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class Story {

    private Long id;

    private String title;

    private String type;

    private String status;

    private Integer testedCaseNum;

    private Integer totalCaseNum;

    private String detail;

    public Story() {

    }

    public Story(Long id, String title, String type, String status) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTestedCaseNum() {
        return testedCaseNum;
    }

    public void setTestedCaseNum(Integer testedCaseNum) {
        this.testedCaseNum = testedCaseNum;
    }

    public Integer getTotalCaseNum() {
        return totalCaseNum;
    }

    public void setTotalCaseNum(Integer totalCaseNum) {
        this.totalCaseNum = totalCaseNum;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
