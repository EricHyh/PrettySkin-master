package com.hyh.prettyskin.demo.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @description
 * @data 2018/8/16
 */

public class UrlUtil {

    //https://www.jianshu.com/p/016b33951717
    //https://wenku.baidu.com/view/8579cc1152d380eb62946d13.html
    private final static List<String> COMMON_DOMAIN_SUFFIX = Arrays.asList(
            ".com",
            ".cn",
            ".net",
            ".org",
            ",top",
            ".vip",
            ".gov",
            ".edu",
            ".cc",
            ".mil",
            ".biz",
            ".info",
            ".name",
            ".museum",
            ".coop",
            ".aero",
            ".xxx",
            ".idv",
            ".tv",
            ".pro",
            ".int");

    private final static String COMMON_HOST_REGEX;

    static {
        StringBuilder sb = new StringBuilder(".+[");
        int size = COMMON_DOMAIN_SUFFIX.size();
        for (int index = 0; index < size; index++) {
            String commonDomainSuffix = COMMON_DOMAIN_SUFFIX.get(index);
            if (index < size - 1) {
                sb.append("\\").append(commonDomainSuffix).append("|");
            } else {
                sb.append("\\").append(commonDomainSuffix).append("]");
            }
        }
        COMMON_HOST_REGEX = sb.toString();
    }

    public static boolean isWebUrl(String input) {
        return !TextUtils.isEmpty(input) && Patterns.WEB_URL.matcher(input).matches();
    }

    public static boolean isIncludeWebUrl(String input) {
        return !TextUtils.isEmpty(findWebUrl(input));
    }

    private static String findWebUrl(String input) {
        if (TextUtils.isEmpty(input)) return null;
        if (isWebUrl(input)) return input;
        Matcher matcher = Patterns.WEB_URL.matcher(input);
        if (matcher.find()) {
            String group = matcher.group();
            if (input.indexOf(group) <= 7 && input.length() - group.length() < 18) {
                return group;
            }
        }
        return null;
    }

    /**
     * 尝试将一些格式不正确的链接转化为格式正确的链接，例如：
     * 1.将 <www.baidu.com> 转化为 <http://www.baidu.com>
     * 2.将 <www.baidu.com 百度> 转化为 <http://www.baidu.com>
     */
    public static String tryConvertToUrl(String input) {
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        String webUrl = findWebUrl(input);
        if (!TextUtils.isEmpty(webUrl)) {
            webUrl = addHttpPrefixIfNeeded(webUrl);
            return matchCommonHost(webUrl);
        }
        return input;
    }

    private static String matchCommonHost(String webUrl) {
        Uri uri = Uri.parse(webUrl);
        String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            return webUrl;
        }
        if (Patterns.IP_ADDRESS.matcher(host).matches()) {
            return webUrl;
        } else {
            if (!webUrl.endsWith(host)) {
                return webUrl;
            } else {
                Pattern pattern = Pattern.compile(COMMON_HOST_REGEX);
                Matcher matcher = pattern.matcher(host);
                if (matcher.find()) {
                    String group = matcher.group();
                    if (host.length() - group.length() > 10) {
                        return webUrl;
                    }
                    return webUrl.replace(host, group);
                }
            }
        }
        return webUrl;
    }

    public static String addHttpPrefixIfNeeded(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (TextUtils.isEmpty(url) || url.startsWith("about:") || url.startsWith("file:")) {
            return url;
        }
        String temp = url.toLowerCase();
        if (temp.startsWith("http") || temp.startsWith("https")) {
            return url;
        }
        if (url.startsWith("://")) {
            url = "http" + url;
        } else if (url.startsWith("//")) {
            url = "http:" + url;
        } else {
            url = "http://" + url;
        }
        return url;
    }
}