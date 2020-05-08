package com.hyh.prettyskin.demo.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hyh.prettyskin.demo.utils.UrlUtil;
import com.hyh.prettyskin.demo.widget.CustomWebView;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.io.File;
import java.lang.ref.WeakReference;


/**
 * @author Administrator
 * @description
 * @data 2017/10/24
 */

public class WebClient {

    private static final String APP_CACHE_DIRNAME = "webCache";

    private Context mContext;

    private CustomWebView mWebView;

    private final WebStateViewManager mWebStateViewManager;

    private String mCurrentUrl;

    private IWebViewClient mWebViewClient;

    private IWebChromeClient mWebChromeClient;

    private IWebDownloadListener mWebDownloadListener = new DefaultWebDownloadListener();

    private View.OnTouchListener mWebTouchListener;

    public void setWebViewClient(IWebViewClient webViewClient) {
        this.mWebViewClient = webViewClient;
    }

    public void setWebChromeClient(IWebChromeClient webChromeClient) {
        this.mWebChromeClient = webChromeClient;
    }

    public void setWebDownloadListener(IWebDownloadListener webDownloadListener) {
        this.mWebDownloadListener = webDownloadListener;
    }

    public WebClient(Context context, CustomWebView webView, View loadingView, View errorView, ProgressBar progressBar) {
        this.mContext = context.getApplicationContext();
        this.mWebView = webView;
        this.mWebStateViewManager = new WebStateViewManager(loadingView, webView, errorView, progressBar);
        init();
    }


    private void init() {
        mWebView.setVisibility(View.VISIBLE);
        mWebStateViewManager.onInitWebView();
        initWebView(mWebView);
    }

    public void setWebTouchListener(View.OnTouchListener webTouchListener) {
        mWebTouchListener = webTouchListener;
        final CustomWebView webView = mWebView;
        if (webView != null) {
            webView.setWebTouchListener(webTouchListener);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.supportMultipleWindows(); //多窗口
        settings.setJavaScriptEnabled(true);//js交互
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setNeedInitialFocus(true); //当webView调用requestFocus时为webView设置节点
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片

        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setBuiltInZoomControls(true);//设置支持缩放
        settings.setSupportZoom(true);//支持缩放
        settings.setDisplayZoomControls(false); //不显示缩放按钮

        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //开启缓存
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = mContext.getCacheDir().getAbsolutePath() + File.separator + APP_CACHE_DIRNAME;
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        webView.setDownloadListener(new DownloadListenerImpl(this));
        webView.setWebViewClient(new WebViewClientImpl(mContext, this));
        webView.setWebChromeClient(new WebChromeClientImpl(this));
        enableCookie(webView);
    }

    private void enableCookie(WebView webView) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }

    //public void

    public void replaceWebView() {
        CustomWebView oldWebView = mWebView;
        if (oldWebView == null) {
            return;
        }
        Context context = oldWebView.getContext();
        ViewParent parent = oldWebView.getParent();
        if (context == null || !(parent instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        ViewGroup.LayoutParams layoutParams = oldWebView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        viewGroup.removeView(oldWebView);
        destroyWebView(oldWebView);

        mWebView = createNewWebView(context, oldWebView.getClass());
        mWebView.setWebTouchListener(mWebTouchListener);

        viewGroup.addView(mWebView, layoutParams);
        initWebView(mWebView);
    }

    private CustomWebView createNewWebView(final Context context, Class<? extends CustomWebView> webViewClass) {
        CustomWebView webView = Reflect.from(webViewClass)
                .constructor()
                .param(Context.class, context)
                .newInstance();
        if (webView == null) {
            webView = new CustomWebView(context);
        }
        return webView;
    }

    public void loadUrl(String url) {
        url = UrlUtil.addHttpPrefixIfNeeded(url);
        mWebView.loadUrl(url);
        mWebStateViewManager.onLoadUrl();
    }

    public void setSupportZoom(boolean support) {
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(support);//设置支持缩放
        settings.setSupportZoom(support);//支持缩放
    }

    public void loadData(String data) {
        mWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        mWebStateViewManager.onLoadUrl();
    }

    public void refresh() {
        mWebView.reload();
        mWebStateViewManager.onLoadUrl();
    }

    public boolean isErrorState() {
        return mWebStateViewManager.isErrorState();
    }

    private boolean shouldOverrideUrlLoading(WebView view, String url) {
        return mWebViewClient != null && mWebViewClient.shouldOverrideUrlLoading(view, url);
    }

    private void onPageStarted(WebView view, String url, Bitmap favicon) {
        mCurrentUrl = url;
        mWebStateViewManager.onPageStart();
        if (mWebViewClient != null) {
            mWebViewClient.onPageStarted(view, url, favicon);
        }
    }

    private void onPageFinished(WebView view, String url) {
        mWebStateViewManager.onPageFinished();
        String title = view.getTitle();
        if (mWebViewClient != null && !"about:blank".equals(title)) {
            mWebViewClient.onPageFinished(view, url);
        }
    }

    private void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (mWebViewClient != null) {
            mWebViewClient.onReceivedError(view, errorCode, description, failingUrl);
        }
        if (!TextUtils.equals(mCurrentUrl, failingUrl)) {
            return;
        }
        if (errorCode == WebViewClient.ERROR_CONNECT
                || errorCode == WebViewClient.ERROR_TIMEOUT
                || errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            mWebStateViewManager.onReceivedError();
        }
    }

    private void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
    }

    private void onProgressChanged(WebView view, int newProgress) {
        mWebStateViewManager.onProgressChanged(newProgress);
        if (mWebChromeClient != null) {
            mWebChromeClient.onProgressChanged(view, newProgress);
        }
    }

    private void onReceivedTitle(WebView view, String title) {
        if (mWebChromeClient != null) {
            mWebChromeClient.onReceivedTitle(view, title);
        }
    }

    private void onReceivedIcon(WebView view, Bitmap icon) {
        if (mWebChromeClient != null) {
            mWebChromeClient.onReceivedIcon(view, icon);
        }
    }

    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return false;
        }
    }

    public void destroy() {
        try {
            if (mWebView != null) {
                ViewParent parent = mWebView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mWebView);
                }
                destroyWebView(mWebView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void destroyWebView(WebView webView) {
        try {
            webView.onPause();
            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWebTitle() {
        if (mWebView != null) {
            return mWebView.getTitle();
        }
        return null;
    }

    public void clearHistory() {
        if (mWebView != null) {
            mWebView.clearHistory();
        }
    }

    public void clearView() {
        if (mWebView != null) {
            mWebView.clearView();
        }
    }


    public void setWebVisibility(int visibility) {
        mWebView.setVisibility(visibility);
    }


    public String getCurrentUrl() {
        return mWebView.getUrl();
    }

    private void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
        if (mWebDownloadListener != null) {
            mWebDownloadListener.onDownloadStart(mWebView, url, userAgent, contentDisposition, mimeType, contentLength);
        }
    }

    public void showErrorView() {
        mWebStateViewManager.onReceivedError();
    }

    public void scrollToTop() {
        mWebView.scrollTo(mWebView.getScrollX(), 0);
    }

    public Context getContext() {
        return mContext;
    }

    private static class DownloadListenerImpl implements DownloadListener {

        WeakReference<WebClient> mWebClientRef;

        DownloadListenerImpl(WebClient webClient) {
            mWebClientRef = new WeakReference<>(webClient);
        }

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onDownloadStart(url, userAgent, contentDisposition, mimeType, contentLength);
            }
        }
    }

    private static class WebViewClientImpl extends WebViewClient {

        Context mContext;

        WeakReference<WebClient> mWebClientRef;

        WebViewClientImpl(Context context, WebClient webClient) {
            mContext = context;
            mWebClientRef = new WeakReference<>(webClient);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebClient webClient = mWebClientRef.get();
            if (webClient != null && webClient.shouldOverrideUrlLoading(view, url)) {
                return true;
            }
            return ShouldOverrideUrlLoadingHelper.override(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onPageFinished(view, url);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.doUpdateVisitedHistory(view, url, isReload);
            }
        }
    }

    private static class WebChromeClientImpl extends WebChromeClient {

        WeakReference<WebClient> mWebClientRef;

        WebChromeClientImpl(WebClient webClient) {
            mWebClientRef = new WeakReference<>(webClient);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onReceivedTitle(view, title);
            }
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            WebClient webClient = mWebClientRef.get();
            if (webClient != null) {
                webClient.onReceivedIcon(view, icon);
            }
        }
    }

    private static class WebStateViewManager {

        View loadingView;

        WebView webView;

        View errorView;

        ProgressBar progressBar;

        boolean isErrorState;

        WebStateViewManager(View loadingView, WebView webView, View errorView, ProgressBar progressBar) {
            this.loadingView = loadingView;
            this.webView = webView;
            this.errorView = errorView;
            this.progressBar = progressBar;
            setVisibility(errorView, View.GONE);
        }

        void onInitWebView() {
            setVisibility(progressBar, View.GONE);
        }

        void onLoadUrl() {
            if (loadingView != null) {
                loadingView.bringToFront();
                setVisibility(loadingView, View.VISIBLE);
            }
            setVisibility(errorView, View.GONE);
            isErrorState = false;
        }

        void onPageStart() {
            setVisibility(progressBar, View.VISIBLE);
        }

        void onProgressChanged(int progress) {
            if (progressBar != null) {
                progressBar.setProgress(progress);
                if (progress >= 100) {
                    setVisibility(progressBar, View.GONE);
                }
            }
            if (progress > 20) {
                setVisibility(loadingView, View.GONE);
            }
        }

        void onPageFinished() {
            setVisibility(progressBar, View.GONE);
            setVisibility(loadingView, View.GONE);
        }

        void onReceivedError() {
            if (errorView != null) {
                errorView.bringToFront();
                setVisibility(errorView, View.VISIBLE);
            }
            setVisibility(loadingView, View.GONE);
            isErrorState = true;
        }

        void setVisibility(View view, int visibility) {
            if (view != null && view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }

        boolean isErrorState() {
            return isErrorState;
        }
    }
}
