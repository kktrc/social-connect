package com.fulaan.connect;

import com.fulaan.exception.ConnectException;
import com.fulaan.model.Sex;
import com.fulaan.model.UserInfo;
import com.fulaan.util.Util;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by moslpc on 2017/1/16.
 */
public class QQAuth implements Auth {

  private static final String PROPERTIES_NAME = "qqconnectconfig.properties";
  private static Properties properties;
  private HttpClient client = new HttpClient();
  private static String APP_ID;
  private static String APP_KEY;
  private static String REDIRECT_URL;
  private static String ACCESS_TOKEN_URL;

  static {
    init();
  }

  private static void init() {
    properties = new Properties();
    try {
      properties.load(QQAuth.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME));
    } catch (IOException e) {
      e.printStackTrace();
    }
    APP_ID = properties.getProperty("app_ID").trim();
    APP_KEY = properties.getProperty("app_KEY").trim();
    REDIRECT_URL = Util.strURLEncodeUTF8(properties.getProperty("redirect_URI").trim());
    ACCESS_TOKEN_URL = properties.getProperty("accessTokenURL").trim();
  }

  public String getAuthUrl() {
    String authorizeURL = properties.getProperty("authorizeURL").trim();
    return authorizeURL + "?client_id=" + APP_ID + "&redirect_uri=" + REDIRECT_URL + "&response_type=" + "code" + "&state=" + "123456";
  }

  public UserInfo getUserInfo(String authCode) throws ConnectException {
    try {
      AccessToken accessToken = getAccessTokenByRequest(authCode);
      if (accessToken == null) {
        throw new ConnectException();
      }
      OpenID openIDObj = new OpenID(accessToken.getAccessToken());
      String openId = openIDObj.getUserOpenID();
      com.qq.connect.api.qzone.UserInfo qzoneUser = new com.qq.connect.api.qzone.UserInfo(accessToken.getAccessToken(), openId);
      UserInfoBean userInfoBean = qzoneUser.getUserInfo();
      UserInfo userInfo = new UserInfo();
      userInfo.setUniqueId(openId);
      userInfo.setNickName(userInfoBean.getNickname());
      userInfo.setSex(Sex.get(userInfoBean.getGender()));
      if (!"".equals(userInfoBean.getAvatar().getAvatarURL100())) {
        userInfo.setAvatar(userInfoBean.getAvatar().getAvatarURL100());
      } else if (!"".equals(userInfoBean.getAvatar().getAvatarURL50())) {
        userInfo.setAvatar(userInfoBean.getAvatar().getAvatarURL50());
      } else {
        userInfo.setAvatar(userInfoBean.getAvatar().getAvatarURL30());
      }
      return userInfo;
    } catch (QQConnectException e) {
      e.printStackTrace();
      throw new ConnectException();
    }
  }

  public UserInfo getUserInfoByWap(String authCode) throws ConnectException {
    return null;
  }

  private AccessToken getAccessTokenByRequest(String authCode) {
    try {
      return new AccessToken(this.client.post(ACCESS_TOKEN_URL, new PostParameter[]{new PostParameter("client_id", APP_ID), new PostParameter("client_secret", APP_KEY), new PostParameter("grant_type", "authorization_code"), new PostParameter("code", authCode), new PostParameter("redirect_uri", REDIRECT_URL)}, Boolean.TRUE));
    } catch (QQConnectException e) {
      e.printStackTrace();
    }
    return null;
  }
}
