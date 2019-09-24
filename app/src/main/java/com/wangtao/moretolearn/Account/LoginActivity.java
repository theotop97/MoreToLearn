package com.wangtao.moretolearn.Account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangtao.moretolearn.R;
import com.wangtao.moretolearn.db.UserInformation;
import com.wangtao.moretolearn.util.BaseActivity;

import org.litepal.crud.DataSupport;


import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button bt_UserRegistration;
    private ImageView iv_Login;
    private EditText et_User, et_Password;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏顶部状态栏，必须写在setContentView方法前
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //隐藏顶部标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initUI();
    }

    /**
     * 初始化UI控件以及设置监听
     */
    private void initUI() {
        bt_UserRegistration = findViewById(R.id.bt_userRegistration);
        iv_Login = findViewById(R.id.iv_login);
        et_User = findViewById(R.id.et_User);
        et_Password = findViewById(R.id.et_Password);

        bt_UserRegistration.setOnClickListener(this);
        iv_Login.setOnClickListener(this);
        iv_Login.setEnabled(false);

        //给输入密码的EditText添加监听，在没有输入时不允许点击ImageView登陆
        et_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_User.getText().toString().isEmpty() && !s.toString().isEmpty()) {
                    iv_Login.setColorFilter(Color.parseColor("#5ECF41"));
                    iv_Login.setEnabled(true);
                } else {
                    iv_Login.setColorFilter(Color.parseColor("#FFFFFF"));
                    iv_Login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //给输入账户的EditText添加监听，在没有输入时不允许点击ImageView登陆，两个都添加了监听是因为避免先输入密码后输入账号导致逻辑混乱
        et_User.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_Password.getText().toString().isEmpty() && !s.toString().isEmpty()) {
                    iv_Login.setColorFilter(Color.parseColor("#5ECF41"));
                    iv_Login.setEnabled(true);
                } else {
                    iv_Login.setColorFilter(Color.parseColor("#FFFFFF"));
                    iv_Login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_userRegistration:
                UserRegistrationActivity.actionStart(this);
                break;
            case R.id.iv_login:
                List<UserInformation> userInformations = DataSupport.findAll(UserInformation.class);
                //表中默认存在一个账号和密码都为admin的用户
                if (userInformations.size() == 0) {
                    UserInformation information = new UserInformation();
                    information.setUserNumber("admin");
                    information.setPassword("admin");
                    information.save();

                }
                for (UserInformation userInformation:userInformations) {
                    if ((userInformation.getUserNumber().equals(et_User.getText().toString())) &&
                            (userInformation.getPassword().equals(et_Password.getText().toString())))  {
                        //进入Activity
                        //finish();
                        Toast.makeText(this,"！",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"账号或密码输入错误！没有账号请先注册！",Toast.LENGTH_SHORT).show();
                    }
                }

            default:
                break;
        }
    }
}
