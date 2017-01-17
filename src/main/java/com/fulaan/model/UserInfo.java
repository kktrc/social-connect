package com.fulaan.model;

/**
 * Created by moslpc on 2017/1/16.
 */
public class UserInfo {

    private String nickName;
    private Sex sex;
    private String avatar;
    private String uniqueId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
