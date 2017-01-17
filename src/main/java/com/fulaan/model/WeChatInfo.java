package com.fulaan.model;

import java.util.List;

public class WeChatInfo {

    private String country;
    private String unionid;
    private String province;
    private String city;
    private String openid;
    private int sex;
    private String nickname;
    private String headimgurl;
    private String language;
    private List<Object> privilege;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return this.headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Object> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<Object> privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "WeChatInfo{" +
                "country='" + country + '\'' +
                ", unionid='" + unionid + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", openid='" + openid + '\'' +
                ", sex=" + sex +
                ", nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", language='" + language + '\'' +
                ", privilege=" + privilege +
                '}';
    }
}
