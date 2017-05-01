package com.tfn.letoutiao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tfn.letoutiao.R;


import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by tf on 2017/4/29.
 */

public class WebViewActivity extends SwipeBackActivity {

    private WebView mWebView;

    private ImageView ivLeftBack;
    private TextView mTitle;
    private ProgressBar mProgressBar;
    private ProgressBar mProgress;

    private static final String TAG = "MainActivity----->";
    private WebSettings mWebViewSettings;

    /**
     * 默认打开的网址
     */
    private String mUrl = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mUrl = getIntent().getStringExtra("url");

        initView();

        initWebView();

        initWebSettings();

        initWebViewClient();

        initWebChromeClient();
    }

    public static void startUrl(Context context, String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /**
     * 初始化 view
     */
    private void initView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        ivLeftBack = (ImageView) findViewById(R.id.iv_left_back);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgress = (ProgressBar) findViewById(R.id.pb_progress);

        ivLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
    }

    /**
     * 初始化 webSetting
     */
    private void initWebSettings() {
        // 支持 JS
        mWebViewSettings.setJavaScriptEnabled(true);

        // 设置缓存
        // LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
        // LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
        // LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式。
        // LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
        // LOAD_CACHE_ELSE_NETWORK：只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        mWebViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 开启DOM storage API 功能
        mWebViewSettings.setDomStorageEnabled(true);

        // 设置 数据库 缓存路径
        mWebViewSettings.setDatabaseEnabled(true);

        // 设置默认编码
        mWebViewSettings.setDefaultTextEncodingName("utf-8");

        // 将图片调整到适合 webView 的大小
        mWebViewSettings.setUseWideViewPort(false);

        // 支持缩放
        mWebViewSettings.setSupportZoom(true);

        // 支持内容重新布局
        mWebViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 多窗口
        mWebViewSettings.setSupportMultipleWindows(true);

        // 设置可以访问文件
        mWebViewSettings.setAllowFileAccess(true);

        // 当 webView 调用 requestFocus 时为 webView 设置节点
        mWebViewSettings.setNeedInitialFocus(true);

        // 设置支持缩放
        mWebViewSettings.setBuiltInZoomControls(true);

        // 支持通过 JS 打开新窗口
        mWebViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 缩放至至屏幕大小
        mWebViewSettings.setLoadWithOverviewMode(true);
    }

    /**
     * 初始化 webView
     */
    private void initWebView() {
        mWebView.loadUrl(mUrl);
        mWebViewSettings = mWebView.getSettings();

        if (Build.VERSION.SDK_INT >= 19){
            // 对于系统API在19以上的版本做了兼容。因为4.4以上的系统在
            // onPageFinished时再恢复图片加载，如果存在多张图片引用的
            // 是相同的src时，会只有一个image标签得到加载，因而对于这样的系统我们就先直接加载。
            mWebViewSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebViewSettings.setLoadsImagesAutomatically(false);
        }

        mWebView.setDownloadListener(new MyDownloadListener());
    }

    /**
     * 初始化 webChromeClient
     */
    private void initWebChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgress.setProgress(newProgress);
            }
        });
    }

    /**
     * 初始化 webViewClient
     */
    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                // return true: 代表在打开新的 url 是 WebView 就不会再加载这个 url 了
                //              所有处理都需要在 WebView中操作，包含加载
                // return false: 则系统就认为上层没有做处理， 接下来还是会继续加载这个 url
                //
                return super.shouldOverrideUrlLoading(view, url);
            }

            // 加载网页时替换某个资源
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = null;
                if (url.contains("logo")){

                }
                return response;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mTitle.setText("正在加载中...");
                mProgressBar.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mTitle.setText(view.getTitle());
                if (!mWebViewSettings.getLoadsImagesAutomatically()){
                    mWebViewSettings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i(TAG, "onReceivedError: ");
            }
        });
    }

    private class MyDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            // 采用系统的download模块
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // mWebView.canGoBack() 在WebView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}