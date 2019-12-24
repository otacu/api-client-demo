package com.sdt.api.client.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SdtStringUtil extends StringUtils {

    /**
     * null 字符串
     */
    public static final String EMPTY_NULL = "null";

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)        = true
     * StringUtils.isEmpty("")          = true
     * StringUtils.isEmpty(" ")         = false
     * StringUtils.isEmpty("bob")       = false
     * StringUtils.isEmpty("  bob  ")   = false
     * StringUtils.isEmpty("null")      = true
     * StringUtils.isEmpty(" null ")    = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0 || EMPTY_NULL.equalsIgnoreCase(cs.toString());
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)       = true
     * StringUtils.isBlank("")         = true
     * StringUtils.isBlank(" ")        = true
     * StringUtils.isBlank("bob")      = false
     * StringUtils.isBlank("  bob  ")  = false
     * StringUtils.isBlank("null")     = true
     * StringUtils.isBlank("  null  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }

        if (EMPTY_NULL.equalsIgnoreCase(cs.toString().trim())) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        return Optional.ofNullable(value).map(s -> s.length()).orElse(0);
    }


    /**
     * toString
     *
     * @param o            对象
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String toStringDefault(Object o, String defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        try {
            return String.valueOf(o);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 替换不可见字符
     *
     * @param str 字符串
     * @return 替换结果
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
