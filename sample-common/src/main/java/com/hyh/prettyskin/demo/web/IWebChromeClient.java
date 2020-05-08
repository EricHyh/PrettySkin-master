package com.hyh.prettyskin.demo.web;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * @author Administrator
 * @description
 * @data 2017/10/24
 */

public interface IWebChromeClient {

    /**
     * Tell the host application the current progress of loading a page.
     *
     * @param view        The WebView that initiated the callback.
     * @param newProgress Current page loading progress, represented by
     *                    an integer between 0 and 100.
     */
    void onProgressChanged(WebView view, int newProgress);

    /**
     * Notify the host application of a change in the document title.
     *
     * @param view  The WebView that initiated the callback.
     * @param title A String containing the new title of the document.
     */
    void onReceivedTitle(WebView view, String title);

    /**
     * Notify the host application of a new favicon for the current page.
     *
     * @param view The WebView that initiated the callback.
     * @param icon A Bitmap containing the favicon for the current page.
     */
    void onReceivedIcon(WebView view, Bitmap icon);
}
