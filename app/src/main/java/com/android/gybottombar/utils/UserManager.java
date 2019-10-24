package com.android.gybottombar.utils;

/**
 * Description: GYBottomBar
 * Created by gy(1984629668@qq.com)
 * Created Time on 2019/10/24 15:48
 */
public class UserManager {
    private static boolean isLogin=false;

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        UserManager.isLogin = isLogin;
    }
}
