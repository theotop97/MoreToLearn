package com.wangtao.moretolearn.util;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * 所有Activity的基类，继承自AppCompatActivity
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //在onCreate时，将当前Activity加入到管理Activity的List中
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在onCreate时，将当前Activity从管理Activity的List中移除
        ActivityCollector.removeActivity(this);
    }
}
