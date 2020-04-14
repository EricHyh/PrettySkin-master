package com.hyh.prettyskin.demo.web;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * @author Administrator
 * @description
 * @data 2019/7/29
 */

public class BaseWebChromeClient implements IWebChromeClient {

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {

    }
}
