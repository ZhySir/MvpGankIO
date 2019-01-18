package com.zhy.mvpgankio.page.web;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.zhy.mvpgankio.R;
import com.zhy.mvpgankio.common.base.activity.BaseActivity;
import com.zhy.mvpgankio.common.utils.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Web网页显示
 * Created by zhy on 2019/1/18.
 */
public class WebActivity extends BaseActivity {

    private ImageView ivLeft;
    private TextView tvTitle;
    private BridgeWebView webView;

    private int type = 0;//必传字段
    private Bundle bundle;
    private boolean needClearHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    public void initParam() {
        super.initParam();
        bundle = getIntent().getExtras();
        type = bundle.getInt("type");
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initView() {
        super.initView();
        ivLeft = findViewById(R.id.ivLeft);
        tvTitle = findViewById(R.id.tvTitle);
        webView = findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // 设置webView的一些属性
        WebSettings webSettings = webView.getSettings();

        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        // 自适应可不设置，但是华为P10有问题
        webSettings.setTextZoom(100);

        //允许与js进行交互
        webSettings.setJavaScriptEnabled(true);

        // 从Android5.0开始，WebView默认不支持同时加载Https和Http混合模式。  url是https,内容http
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.addJavascriptInterface(new JavaScriptInterface(this), "gankio");

        // pdf使用
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient() {

            // 通过重写WebViewClient的onReceivedSslError方法来接受所有网站的证书，忽略SSL错误。
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (!TextUtils.isEmpty(url) && url.contains(".pdf")) {
                    loadPdfFile(view, url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);
                if (needClearHistory) {
                    needClearHistory = false;
                    view.clearHistory();//清除历史记录
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        switch (type) {
            case 1:
                tvTitle.setText(bundle.getString("title"));
                webView.loadUrl(bundle.getString("url"));
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        ivLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivLeft:
                if (webView.canGoBack()) {
                    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    webView.goBack();
                    return;
                }
                finish();
                break;
        }
    }

    public void loadPdfFile(WebView view, String docPath) {
        if (!"".equals(docPath)) {
            byte[] bytes = null;
            try {//获取以字符编码为utf-8的字符
                bytes = docPath.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            if (bytes != null) {
                docPath = new BASE64Encoder().encode(bytes);// BASE64转码
            }
        }
        view.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + docPath);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) webView.getParent()).removeView(webView);
            }
            webView.stopLoading();
            //退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}
