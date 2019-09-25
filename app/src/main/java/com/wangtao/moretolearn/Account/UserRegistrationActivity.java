package com.wangtao.moretolearn.Account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangtao.moretolearn.R;
import com.wangtao.moretolearn.db.UserInformation;
import com.wangtao.moretolearn.util.ActivityCollector;
import com.wangtao.moretolearn.util.BaseActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class UserRegistrationActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_newUserNumber, et_newPassword, et_ConfirmPassword;
    private ImageView iv_Confirm, iv_PasswordCorrect, iv_UserNumberCorrect;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserRegistrationActivity.class);
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
        initUI();

    }

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
        et_newUserNumber = findViewById(R.id.et_newUserNumber);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_ConfirmPassword = findViewById(R.id.et_confirmPassword);
        iv_PasswordCorrect = findViewById(R.id.iv_PasswordCorrect);
        iv_UserNumberCorrect = findViewById(R.id.iv_UserNumberCorrect);
        iv_Confirm = findViewById(R.id.iv_confirm);
        iv_Confirm.setEnabled(false);
        et_newPassword.setEnabled(false);
        et_ConfirmPassword.setEnabled(false);
        iv_Confirm.setOnClickListener(this);
        //注册账号的账号EditText监听
        et_newUserNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    et_newPassword.setEnabled(true);
                } else {
                    et_newPassword.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUserNumberCorrect(et_newUserNumber.getText().toString())) {
                    iv_UserNumberCorrect.setColorFilter(Color.parseColor("#5ECF41"));
                } else {
                    iv_Confirm.setEnabled(false);
                    iv_Confirm.setColorFilter(Color.parseColor("#FFFFFF"));
                    iv_UserNumberCorrect.setColorFilter(Color.parseColor("#FFFFFF"));
                }
                if (s.length() > 0) {
                    et_newPassword.setEnabled(true);
                } else {
                    et_newPassword.setEnabled(false);
                    et_newPassword.setText("");

                }


            }
        });
        //注册账号的密码EditText监听
        et_newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    et_ConfirmPassword.setEnabled(true);
                } else {
                    et_ConfirmPassword.setEnabled(false);
                    et_ConfirmPassword.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && s.toString().equals(et_ConfirmPassword.getText().toString())) {
                    iv_Confirm.setEnabled(true);
                    iv_Confirm.setColorFilter(Color.parseColor("#5ECF41"));
                    iv_PasswordCorrect.setColorFilter(Color.parseColor("#5ECF41"));
                } else {
                    iv_Confirm.setEnabled(false);
                    iv_Confirm.setColorFilter(Color.parseColor("#FFFFFF"));
                    iv_PasswordCorrect.setColorFilter(Color.parseColor("#FFFFFF"));
                }
            }

        });
        //注册账号的确认密码EditText监听
        et_ConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(et_newPassword.getText().toString()) && !et_ConfirmPassword.getText().toString().equals("")) {
                    iv_Confirm.setEnabled(true);
                    iv_PasswordCorrect.setEnabled(true);
                    iv_Confirm.setColorFilter(Color.parseColor("#5ECF41"));
                    iv_PasswordCorrect.setColorFilter(Color.parseColor("#5ECF41"));
                } else {
                    iv_Confirm.setEnabled(false);
                    iv_PasswordCorrect.setEnabled(false);
                    iv_Confirm.setColorFilter(Color.parseColor("#FFFFFF"));
                    iv_PasswordCorrect.setColorFilter(Color.parseColor("#FFFFFF"));
                }

            }
        });
    }

    /**
     * 由于账号不能重复，所以在注册账号时检查该账号是否存在
     *
     * @param userNumber 想要注册的账号信息
     * @return 账号可用返回true，否则返回false
     */
    private boolean isUserNumberCorrect(String userNumber) {
        if (userNumber.isEmpty()) {
            return false;
        }
        List<UserInformation> userInformations = DataSupport.findAll(UserInformation.class);
        for (UserInformation userInformation : userInformations) {
            if (userInformation.getUserNumber().equals(userNumber)) {
                return false;
            }
        }
        return true;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_confirm:
                UserInformation information = new UserInformation();
                information.setUserNumber(et_newUserNumber.getText().toString());
                information.setPassword(et_ConfirmPassword.getText().toString());
                information.save();
                LoginActivity.actionStart(this);
                ActivityCollector.removeActivity(this);
                Toast.makeText(this,"注册成功，现在可以登录啦!",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}

