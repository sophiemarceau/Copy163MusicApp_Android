package com.sophiemarceauqu.qxb_voice.api;

import com.sophiemarceauqu.lib_network.okhttp.CommonOkHttpClient;
import com.sophiemarceauqu.lib_network.okhttp.request.CommonRequest;
import com.sophiemarceauqu.lib_network.okhttp.request.RequestParams;
import com.sophiemarceauqu.lib_network.okhttp.response.listener.DisposeDataHandle;
import com.sophiemarceauqu.lib_network.okhttp.response.listener.DisposeDataListener;
import com.sophiemarceauqu.qxb_voice.model.discory.BaseRecommandModel;
import com.sophiemarceauqu.qxb_voice.model.discory.BaseRecommandMoreModel;
import com.sophiemarceauqu.qxb_voice.model.friend.BaseFriendModel;
import com.sophiemarceauqu.qxb_voice.view.login.user.User;

public class RequestCenter {
    static class HttpConstants {
        private static final String ROOT_URL = "http://imooc.com/api";
//        private static final String ROOT_URL ="http://39.97.122.129";

        /**
         * 首页请求接口
         */
        private static String HOME_RECOMMAND = ROOT_URL + "/product/home_recommand.php";

        private static String HOME_FRIEND = ROOT_URL + "/product/home_friend.php";

        private static String HOME_RECOMMAND_MORE = ROOT_URL + "/product/home_recommand_more.php";

        /**
         * 登陆接口
         */
        public static String LOGIN = ROOT_URL + "/user/login_phone.php";
    }

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void login(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("mb", "18734924592");
        params.put("pwd", "999999q");
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }

    public static void requestRecommadnMore(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND_MORE, null, listener, BaseRecommandMoreModel.class);
    }

    public static void requestFriendData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_FRIEND, null, listener, BaseFriendModel.class);
    }
}
