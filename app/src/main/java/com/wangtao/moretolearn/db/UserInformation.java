package com.wangtao.moretolearn.db;

import org.litepal.crud.DataSupport;


public class UserInformation extends DataSupport {
    private String UserNumber;

    public String getUserNumber() {
        return UserNumber;
    }

    public void setUserNumber(String userNumber) {
        UserNumber = userNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    private String Password;
}
