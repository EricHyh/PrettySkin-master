package com.hyh.prettyskin.demo.web;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * @author Administrator
 * @description
 * @data 2017/10/24
 */

public interface IWebViewClient {

    boolean shouldOverrideUrlLoading(WebView view, String url);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

}
