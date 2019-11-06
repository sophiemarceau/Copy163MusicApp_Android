package com.sophiemarceauqu.qxb_voice.view.login.manager;

import com.sophiemarceauqu.qxb_voice.view.login.user.User;

//单例管理登录用户信息
public class UserManager {
    private  static  UserManager mInstance;
    private User mUser;

    public static UserManager getInstance(){
        if (mInstance==null){
            synchronized (UserManager.class){
                if (mInstance == null){
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    //保存用户信息到内存
    public  void saveUser(User user){
        mUser = user;

    }

    //持久化用户信息
    private void saveLocal(User user){

    }

    //获取用户信息
    public User getUser(){
        return mUser;
    }

    //从本地获取
    private  User getLocal(){
        return  null;
    }

    //判断是否登陆过
    public boolean hasLogin(){
        return  getUser() == null ? false :true;
    }

    //从库中删除用户信息
    private void removeLocal(){

    }
}
