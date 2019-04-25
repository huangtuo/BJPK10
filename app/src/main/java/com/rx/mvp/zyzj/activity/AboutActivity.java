package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.HttpUrls;
import com.rx.mvp.zyzj.widget.ProgressWebView;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;

    @BindView(R.id.about_webView)
    ProgressWebView webView;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("关于我们");

        imgBack.setOnClickListener(v -> {
            goBack();
        });
    }

    @Override
    protected void initData() {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");// 已utf-8格式解析
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 适应屏幕，内容将自动换行
        settings.setUseWideViewPort(false);// 设置支持双击放大和缩小 设置适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl(HttpUrls.ABOUT_URL);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack(){
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }
}
