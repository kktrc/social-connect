package com.fulaan.factory;

import com.fulaan.connect.Auth;
import com.fulaan.connect.QQAuth;
import com.fulaan.connect.WeChatAuth;

/**
 * Created by moslpc on 2017/1/17.
 */
public class AuthFactory {

    public static Auth getQQAuth() {
        return new QQAuth();
    }

    public static Auth getWechatAuth() {
        return new WeChatAuth();
    }
}
