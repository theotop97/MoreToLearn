package com.wangtao.moretolearn.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.wangtao.moretolearn.db.UserInformation;

import org.litepal.tablemanager.Connector;

/**
 * 所有Activity的基类，继承自AppCompatActivity
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在onCreate时，将当前Activity加入到管理Activity的List中
        ActivityCollector.addActivity(this);
//        if (Connector.getDatabase() == null) {
//            Connector.getDatabase();
//            UserInformation information = new UserInformation();
//            information.setUserNumber("admin");
//            information.setPassword("admin");
//            boolean is = information.save();
//        }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在onCreate时，将当前Activity从管理Activity的List中移除
        ActivityCollector.removeActivity(this);
    }
}
