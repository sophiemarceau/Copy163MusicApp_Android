package com.sophiemarceauqu.qxb_voice.utils;

import com.sophiemarceauqu.qxb_voice.model.user.User;

public class UserManager {
    private static UserManager userManager = null;
    private User user = null;

    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    userManager = new UserManager();
                }
                return userManager;
            }
        }
        return userManager;
    }

    //init user
    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasLogined() {
        return user == null ? false : true;
    }

    public User getUser() {
        return this.user;
    }

    public void removeUser() {
        this.user = null;
    }
}
