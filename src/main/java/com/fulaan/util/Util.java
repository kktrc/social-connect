package com.fulaan.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by moslpc on 2017/1/16.
 */
public class Util {

    public static String strURLEncodeUTF8(String value){
        try {
            return URLEncoder.encode(value, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
