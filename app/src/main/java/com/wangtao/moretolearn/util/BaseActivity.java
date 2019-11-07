package com.wangtao.moretolearn.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wangtao.moretolearn.Account.LoginActivity;


/**
 * 所有Activity的基类，继承自AppCompatActivity
 */

public class BaseActivity extends AppCompatActivity {
    private ForceOffLineReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //以下代码将安卓6.0以上手机状态栏设置为沉浸式，目前三星手机测试通过，其他型号的未测试
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#F7F7F7"));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //在onCreate时，将当前Activity加入到管理Activity的List中
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.moretolearn.navigationview.FORCE_OFFLINE");
        receiver = new ForceOffLineReceiver();
        registerReceiver(receiver,intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在onDestroy时，将当前Activity从管理Activity的List中移除
        ActivityCollector.removeActivity(this);
    }

    /*
    * 接受一条广播下线，之前直接使用ActivityCollector.finishAll()方法
    * 在三星S8+，android 9.0，OneUI 1.0上直接使用返回键又会进入到主界面
    * */
    class ForceOffLineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityCollector.finishAll();
            Intent it = new Intent(context, LoginActivity.class);
            context.startActivity(it);
        }
    }
}
