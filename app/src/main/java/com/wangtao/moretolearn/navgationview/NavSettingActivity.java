package com.wangtao.moretolearn.navgationview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.wangtao.moretolearn.Account.LoginActivity;
import com.wangtao.moretolearn.R;
import com.wangtao.moretolearn.util.BaseActivity;

public class NavSettingActivity extends BaseActivity implements View.OnClickListener{

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NavSettingActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式布局
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#F7F7F7"));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_nav_setting_activity);
        initUI();
    }

    public void initUI() {
        RelativeLayout rl_Logout = findViewById(R.id.rl_Logout);
        rl_Logout.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Logout:

                View alterDialogLogout = getLayoutInflater().inflate(R.layout.alertdialog_logout, null, false);
                Button bt_ad_cancel = alterDialogLogout.findViewById(R.id.bt_logout_cancel);
                Button bt_ad_confirm = alterDialogLogout.findViewById(R.id.bt_logout_confirm);
                final AlertDialog.Builder builder = new AlertDialog.Builder(NavSettingActivity.this);
                builder.setView(alterDialogLogout);

                final Dialog dialog = builder.show();
                bt_ad_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                bt_ad_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = LoginActivity.getSharedPreferences();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin",false);
                        editor.apply();
                        sendBroadcast(new Intent("com.moretolearn.navigationview.FORCE_OFFLINE"));
//                        ActivityCollector.finishAll();
//                        LoginActivity.actionStart(NavSettingActivity.this);
                        dialog.dismiss();
                    }
                });


                break;
            default:
                break;
        }

    }
}