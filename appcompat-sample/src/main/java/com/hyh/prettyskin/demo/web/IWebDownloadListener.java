package com.hyh.prettyskin.demo.web;

import android.webkit.WebView;

/**
 * @author Administrator
 * @description
 * @data 2019/1/17
 */

public interface IWebDownloadListener {

    void onDownloadStart(WebView webView, String url, String userAgent, String contentDisposition, String mimeType, long contentLength);

}
