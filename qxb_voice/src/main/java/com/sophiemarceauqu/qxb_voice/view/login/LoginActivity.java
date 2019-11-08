package com.sophiemarceauqu.qxb_voice.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sophiemarceauqu.lib_common_ui.base.BaseActivity;
import com.sophiemarceauqu.qxb_voice.R;
import com.sophiemarceauqu.qxb_voice.view.login.inter.IUserLoginView;
import com.sophiemarceauqu.qxb_voice.view.login.presenter.UserLoginPresenter;

public class LoginActivity extends BaseActivity implements IUserLoginView {
    private UserLoginPresenter mUserLoginPresenter;

    public static void start(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        mUserLoginPresenter = new UserLoginPresenter(this);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLoginPresenter.login(getUserName(),getPassword());
            }
        });
    }

    @Override
    public String getUserName() {
        return "18734924592";
    }

    @Override
    public String getPassword() {
        return "999999q";
    }

    @Override
    public void showLoadingView() {
        //显示加载中UI
    }

    @Override
    public void hideLoadingView() {
        //隐藏加载布局
    }

    @Override
    public void showLoginFailedView() {
        //登陆失败处理
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
