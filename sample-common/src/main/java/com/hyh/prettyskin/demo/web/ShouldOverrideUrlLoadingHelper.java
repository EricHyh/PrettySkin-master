package com.hyh.prettyskin.demo.web;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import com.hyh.prettyskin.demo.utils.PackageUtil;

/**
 * @author Administrator
 * @description
 * @data 2019/8/15
 */

public class ShouldOverrideUrlLoadingHelper {

    public static boolean override(WebView view, String url) {
        if (view == null) {
            return false;
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return false;
        }
        Intent intent;
        if (url.startsWith("tel:")) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
        }
        boolean activityIntentExist = PackageUtil.isActivityIntentExist(view.getContext(), intent);
        if (activityIntentExist) {
            try {
                view.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}