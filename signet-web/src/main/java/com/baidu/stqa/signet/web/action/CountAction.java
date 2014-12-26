/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.stqa.signet.web.service.CountService;
import com.baidu.stqa.signet.web.vo.Count;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

@Controller("countAction")
@RequestMapping(value = "/signet", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountAction extends BaseAction {

    @Autowired
    private CountService countService;

    /**
     * 获得系统使用统计数据
     * 
     * @param date
     * @param line
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Count>> count(String date, String line) {
        if (date == null) {
            if (line == null) {
                List<Count> result = countService.countAllVisit();
                return new ResponseEntity<List<Count>>(result, HttpStatus.OK);
            } else {
                List<Count> result = countService.countAllLine();
                return new ResponseEntity<List<Count>>(result, HttpStatus.OK);
            }

        } else {
            List<Count> result = countService.countNewlyVisit(date);
            return new ResponseEntity<List<Count>>(result, HttpStatus.OK);
        }
    }

}
