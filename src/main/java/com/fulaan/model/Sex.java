package com.fulaan.model;

/**
 * Created by moslpc on 2017/1/17.
 */
public enum Sex {

    MALE("男",1),
    FEMALE("女",0);

    private int type;
    private String sex;

    Sex(String sex,int type){
        this.sex = sex;
        this.type = type;
    }

    public static Sex get(String sex) {
        if(!"".equals(sex) && sex.equals("男")) {
            return MALE;
        }
        return FEMALE;
    }

    public static Sex get(int type) {
        return type == 1 ? MALE : FEMALE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
