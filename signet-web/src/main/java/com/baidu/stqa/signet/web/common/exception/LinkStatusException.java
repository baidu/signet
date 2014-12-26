/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.common.exception;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class LinkStatusException extends RuntimeException {

    private static final long serialVersionUID = 2067657452739248674L;

    public LinkStatusException(String message) {
        super(message);
    }

}
