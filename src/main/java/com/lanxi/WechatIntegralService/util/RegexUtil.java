package com.lanxi.WechatIntegralService.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean matches(String checkStr, String regexStr) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(checkStr);
        return matcher.matches();
    }
}
