package com.fulaan.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by moslpc on 2017/1/17.
 */
public class HttpClient {

    private static CloseableHttpClient http = HttpClientBuilder.create().build();

    public static String get(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = http.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            return getStrFromInputStream(entity.getContent());
        }
        return null;
    }

    private static String getStrFromInputStream(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  http请求
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url,Map<String,String> params)  throws IOException{
        String encodeUrl = url + "?" + urlEncode(params);
        return get(encodeUrl);
    }

    /**
     * encode url
     * @param map
     * @return
     */
    public static String urlEncode(Map<String,String> map) {
        String retVal = "";
        if(map == null) return retVal;
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = map.get(key);
            try {
                String encodeValue = URLEncoder.encode(value, "UTF8");
                retVal += key + "=" + encodeValue + "&";
            } catch (UnsupportedEncodingException ex) {
            }
        }
        return retVal.substring(0,retVal.length() - 1);
    }

}
