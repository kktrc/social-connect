package com.fulaan.connect;

import com.fulaan.exception.ConnectException;
import com.fulaan.http.HttpClient;
import com.fulaan.model.Sex;
import com.fulaan.model.TokenObj;
import com.fulaan.model.UserInfo;
import com.fulaan.model.WeChatInfo;
import com.fulaan.util.JsonUtil;
import com.fulaan.util.Util;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by moslpc on 2017/1/16.
 */
public class WeChatAuth implements Auth {

    private static String APP_ID;
    private static String APP_SECRET;
    private static String ACCESS_TOKEN_URL;
    private static String REDIRECT_URL;
    private static String CONNECT_URL;
    private static String USER_INFO_URL;
    private static String WAP_APP_ID;
    private static String WAP_APP_SECRET;

    static {
       init();
    }

    private static void init() {
        Properties properties = new Properties();
        try {
            properties.load(QQAuth.class.getClassLoader().getResourceAsStream("wechatconnectconfig.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ACCESS_TOKEN_URL = properties.getProperty("accessTokenURL").trim();
        APP_ID = properties.getProperty("app_ID").trim();
        REDIRECT_URL = properties.getProperty("redirect_URI").trim();
        WAP_APP_ID = properties.getProperty("wap_app_ID").trim();
        WAP_APP_SECRET = properties.getProperty("wap_app_SECRET").trim();
        CONNECT_URL = properties.getProperty("connectURL").trim();
        APP_SECRET = properties.getProperty("app_SECRET").trim();
        USER_INFO_URL = properties.getProperty("getUserInfoURL").trim();
    }

    @Override
    public String getAuthUrl() {
        return String.format(CONNECT_URL, APP_ID, Util.strURLEncodeUTF8(REDIRECT_URL));
    }

    private TokenObj getAccessToken(String authCode,String pf) throws IOException {
        Map<String, String> parms = new HashMap<String, String>();
        if(StringUtils.isNotBlank(pf) && pf.equals("wap")) {
            parms.put("appid", WAP_APP_ID);
            parms.put("secret", WAP_APP_SECRET);
        } else {
            parms.put("appid", APP_ID);
            parms.put("secret", APP_SECRET);
        }
        parms.put("code", authCode);
        parms.put("grant_type", "authorization_code");
        String content = HttpClient.get(ACCESS_TOKEN_URL,parms);
        return JsonUtil.fromJson(content, TokenObj.class);
    }

    private UserInfo getUserInfo(TokenObj tokenObj) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", tokenObj.getAccess_token());
        map.put("openid", tokenObj.getUnionid());
        try {
            String content = HttpClient.get(USER_INFO_URL, map);
            WeChatInfo weChatInfo = JsonUtil.fromJson(content,WeChatInfo.class);
            return convertToUserInfo(weChatInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserInfo convertToUserInfo(WeChatInfo weChatInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatar(weChatInfo.getHeadimgurl());
        userInfo.setSex(Sex.get(weChatInfo.getSex()));
        userInfo.setNickName(weChatInfo.getNickname());
        userInfo.setUniqueId(weChatInfo.getUnionid());
        return userInfo;
    }

    @Override
    public UserInfo getUserInfo(String authCode) {
        try {
            TokenObj tokenObj = getAccessToken(authCode,"pc");
            return getUserInfo(tokenObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserInfo getUserInfoByWap(String authCode) throws ConnectException {
        try {
            TokenObj tokenObj = getAccessToken(authCode,"wap");
            return getUserInfo(tokenObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
