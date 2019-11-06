package com.sophiemarceauqu.qxb_voice.view.login.presenter;

import com.google.gson.Gson;
import com.sophiemarceauqu.lib_network.okhttp.response.listener.DisposeDataListener;
import com.sophiemarceauqu.qxb_voice.api.MockData;
import com.sophiemarceauqu.qxb_voice.api.RequestCenter;
import com.sophiemarceauqu.qxb_voice.model.user.User;
import com.sophiemarceauqu.qxb_voice.utils.UserManager;
import com.sophiemarceauqu.qxb_voice.view.login.inter.IUserLoginPresenter;
import com.sophiemarceauqu.qxb_voice.view.login.inter.IUserLoginView;
import com.sophiemarceauqu.qxb_voice.view.login.user.LoginEvent;

import org.greenrobot.eventbus.EventBus;

public class UserLoginPresenter implements IUserLoginPresenter, DisposeDataListener {
    private IUserLoginView mIView;

    public UserLoginPresenter(IUserLoginView iView) {
        mIView = iView;
    }

    @Override
    public void onSuccess(Object responseObj) {
        mIView.hideLoadingView();
        User user = (User) responseObj;
        UserManager.getInstance().setUser(user);
        //发送登录Event
        EventBus.getDefault().post(new LoginEvent());
        mIView.finishActivity();
    }

    @Override
    public void onFailure(Object responseObj) {
        mIView.hideLoadingView();
        onSuccess(new Gson().fromJson(MockData.LOGIN_DATA, User.class));
        mIView.showLoginFailedView();
    }

    @Override
    public void login(String usernmae, String password) {
        mIView.showLoadingView();
        RequestCenter.login(this);
    }
}
