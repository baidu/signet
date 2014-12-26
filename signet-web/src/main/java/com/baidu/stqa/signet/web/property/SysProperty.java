/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.property;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class SysProperty {
	
	public static String imgPath = "";

    public static String getImgPath() {
        return imgPath;
    }

    public static void setImgPath(String imgPath) {
        SysProperty.imgPath = imgPath;
    }
	
}
