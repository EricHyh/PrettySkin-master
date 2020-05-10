package com.hyh.prettyskin.demo.web;

import android.graphics.Bitmap;
import android.webkit.WebView;



public interface IWebViewClient {

    boolean shouldOverrideUrlLoading(WebView view, String url);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

}
