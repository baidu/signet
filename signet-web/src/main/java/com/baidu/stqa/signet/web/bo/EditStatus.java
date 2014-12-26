/*  
 *  Copyright(C) 2012-2015 Baidu Group
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *  
 */
package com.baidu.stqa.signet.web.bo;

/**
 * @author suhanyuan
 * @version 3.0.0.0
 */

public class EditStatus {

    private String editUserName;
    private boolean isCardCanEdit;

    public String getEditUserName() {
        return editUserName;
    }

    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }

    public boolean isCardCanEdit() {
        return isCardCanEdit;
    }

    public void setCardCanEdit(boolean isCardCanEdit) {
        this.isCardCanEdit = isCardCanEdit;
    }
}
