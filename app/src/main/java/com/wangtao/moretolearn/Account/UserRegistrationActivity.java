package com.wangtao.moretolearn.Account;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.wangtao.moretolearn.R;

public class UserRegistrationActivity extends AppCompatActivity {
    public static void actionStart(Context context) {
        Intent intent = new Intent(context,UserRegistrationActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏顶部状态栏，必须写在setContentView方法前
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_registration);
        //隐藏顶部标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}

