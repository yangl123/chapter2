package com.yang.chapter2.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class StringUtil {
    public static boolean isEmpty(String str){
        if(str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
