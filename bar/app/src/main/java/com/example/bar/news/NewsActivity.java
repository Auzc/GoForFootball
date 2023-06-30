package com.example.bar.news;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.R;

public class NewsActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        // 获取布局文件中的WebView控件
        mWebView = (WebView) findViewById(R.id.webview);

        ;
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true); // 启用JavaScript

        String deviceName = Build.DEVICE; // 获取设备名称
        String osVersion = Build.VERSION.RELEASE; // 获取操作系统版本号
// 拼接User-Agent字符串
        String newUserAgent = "Mozilla/5.0 (Linux; Android " + osVersion + "; " + deviceName + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36";

// 将新User-Agent设置到WebView中
        mWebView.getSettings().setUserAgentString(newUserAgent);


        // 设置WebViewClient以便在WebView控件中显示网页内容，而不是通过外部浏览器显示
        mWebView.setWebViewClient(new WebViewClient());
        if(url!=null){
            mWebView.loadUrl(url);
        }else{
            // 加载指定的URL
            mWebView.loadUrl("https://www.dongqiudi.com/articles/3310135.html");
        }

        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    // 在返回按钮按下后，如果可以导航到上一个网页，则导航到上一个网页
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}