package com.hhcx.hhcx_report.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Cookie操作工具类
 * Created by xuht on 2016/12/30.
 */
public class CookieUtils {

    /**
     * 设置 Cookie, 过期时间自定义
     *
     * @param name   名称
     * @param value  值
     * @param maxAge 过期时间, 单位秒
     */
    public static Cookie addCookie(HttpServletResponse response, String name, String value, String path, String domain, int maxAge) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(maxAge);
            if (null != path) {
                cookie.setPath(path);
            }
            if (null != domain){
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookie;
    }


    /**
     * 设置 Cookies, 过期时间自定义
     *
     * @param response 响应对象
     * @param values   值
     * @param path     路径
     * @param maxAge   过期时间, 单位秒
     * @return Cookies
     */
    public static ArrayList<Cookie> addCookies(HttpServletResponse response, Map<String, String> values, String path, String domain, int maxAge) {
        Set<Map.Entry<String, String>> entries = values.entrySet();
        ArrayList<Cookie> cookies = new ArrayList<Cookie>();
        try {
            for (Map.Entry<String, String> entry : entries) {
                Cookie cookie = new Cookie(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
                cookie.setMaxAge(maxAge);
                if (null != path) {
                    cookie.setPath(path);
                }
                if (null != domain){
                    cookie.setDomain(domain);
                }
                response.addCookie(cookie);
                cookies.add(cookie);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookies;
    }

    /**
     * 获得指定Cookie的值
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        return getCookieValue(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除。
     *
     * @param name 名称
     * @return 值
     */
    public static void deleteCookie(HttpServletResponse response, String name, String domain, String path) {
        Cookie newCookie = new Cookie(name,null);
        newCookie.setMaxAge(0);
        newCookie.setDomain(domain);
        newCookie.setPath(path);
        response.addCookie(newCookie);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request   请求对象
     * @param response  响应对象
     * @param name      名字
     * @param isRemoved 是否移除
     * @return 值
     */
    public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemoved) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isRemoved) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    return value;
                }
            }
        }
        return value;
    }

    /**
     * 获取请求的域名
     * 如果是IP，返回IP地址。
     * @param request 请求对象
     * @return
     */
    public static String getDomainName(HttpServletRequest request){
        String result;
        StringBuffer url = request.getRequestURL();
        String hostname = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString().trim().substring(7);
        result = hostname;
        if(!hostname.contains(":")){
            if(!StringUtil.isIp(hostname)){
                if (hostname.split("\\.").length <= 2){
                    result = hostname;
                }else{
                    //说明是域名
                    result = hostname.substring(hostname.indexOf(".") + 1);
                }
            }
        }
        return result;
    }
}
