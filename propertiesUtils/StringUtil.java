package com.hhcx.hhcx_report.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuht on 2016/12/29.
 */
public class StringUtil {
    /**
     * 判断一个字符串是否为IP地址
     * @param ip
     * @return
     */
    public static boolean isIp(String ip){//判断是否是一个IP
        boolean flag = false;
        ip = ip.trim();
        if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String s[] = ip.split("\\.");
            if(Integer.parseInt(s[0])<255)
                if(Integer.parseInt(s[1])<255)
                    if(Integer.parseInt(s[2])<255)
                        if(Integer.parseInt(s[3])<255)
                            flag = true;
        }
        return flag;
    }


    /**
     * 获取字符串中第Ｎ次出现字符的位置
     * @param str 字符串
     * @param queryStr 查询出现的字符
     * @param num 第几次出现
     * @return
     */
    public static int getCharacterPosition(String str,String queryStr, int num){
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile(queryStr).matcher(str);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if(mIdx == num){
                break;
            }
        }
        //防止没有第N次的字符，导致报错
        if(mIdx < num){
            return -1;
        }
        return slashMatcher.start();
    }
}
