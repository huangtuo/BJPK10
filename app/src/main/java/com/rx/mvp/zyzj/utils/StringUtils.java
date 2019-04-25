package com.rx.mvp.zyzj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 检查密码长度为6-20 并且是数字加字母组合的形式
     *
     * @return
     */
    public static boolean checkPsw(String str) {
        return str.matches("(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,20}");
    }
    /**
     * 检查用户名长度为6-20 并且是数字加字母组合的形式
     *
     * @return
     */
    public static boolean checkUserName(String str) {
        return str.matches("(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,20}");
    }

    /**
     * 判断是否为中文
     *
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /*
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 手机号验证--只验证是11位数字，其他都不做验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("[0-9]{11}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 判断身份证
     *
     * @param str
     * @return
     */
    public static boolean isCardID(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     *
     * @param s
     * @return
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 校验Tag Alias 只能是数字,英文字母和中文
     *
     * @param s
     * @return
     */
    public static boolean isValidTagAndAliasNickname(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 校验推荐人编码 首字母大写+4位数字
     */
    public static boolean isReferCode(String ss) {
        Pattern p = Pattern.compile("[A-Z]{1}\\d{4}");
        Matcher m = p.matcher(ss);
        return m.matches();
    }


    /***
     * 验证中文名字
     *
     * @param name
     * @return
     */
    public static boolean isChineseName(String name) {
        Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String getTwoDecimals(String Decimal) {
        if (Double.parseDouble(Decimal) == 0) {
            return "0";
        }
        if (Decimal.indexOf(".") + 3 <= Decimal.length()) {
            return Decimal.substring(0, Decimal.indexOf(".") + 3);
        }
        return Decimal;
    }
}
