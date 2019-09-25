package com.wangtao.moretolearn.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }



    public static SharedPreferences sharedPreferences;
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
        if (sharedPreferences.getBoolean("isLogin", false)) {
            //在这里直接进入主Activity
            Toast.makeText(this,"123",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    当点击EditText以外的区域关闭输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHindeInput(v, ev)) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (im != null) {
                    im.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }


        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否需要隐藏输入法，当点击的区域不是EditText中是返回true，否则返回false
     *
     * @param v     EditText的父布局
     * @param event MotionEvent的类型
     * @return 需要隐藏返回true，不需要返回false
     */
    private boolean isShouldHindeInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取当前输入框的位置
            v.getLocationInWindow(leftTop);

            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 初始化UI控件以及设置监听
     */
    private void initUI() {
        sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);

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
                //这里如果输入了账号密码会把这两个传到注册的Activity里，并且把密码的edittext清空
                //后续的
                UserRegistrationActivity.actionStart(this);
                et_Password.setText("");
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
                for (UserInformation userInformation : userInformations) {
                    if ((userInformation.getUserNumber().equals(et_User.getText().toString())) &&
                            (userInformation.getPassword().equals(et_Password.getText().toString()))) {
                        //进入Activity
                        //finish();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin", true);
                        editor.putString("userNumber", userInformation.getUserNumber());
                        editor.putString("password", userInformation.getPassword());
                        editor.apply();

                        Toast.makeText(this, "！", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(this, "账号不存在或密码输入错误!", Toast.LENGTH_SHORT).show();
                    }
                }

            default:
                break;
        }
    }
}
