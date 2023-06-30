package com.example.bar.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class SelectPlaceActivity extends AppCompatActivity {
    private WebView mWebView;
    private ImageButton btn_back;
    private ImageButton btn_ok;

    private Float lat=null;
    private Float lng=null;
    private String address=null;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_ok=findViewById(R.id.ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lat == null){
                    Toast.makeText(SelectPlaceActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                }else{
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("lat", lat);
                    resultIntent.putExtra("lng", lng);
                    resultIntent.putExtra("address", address);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                finish();
            }
        });


        String mUrl = "https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://callback&key=QULBZ-6M6KO-5YZWR-SEYTJ-GNNS5-O6B3L&referer=myapp";
        mWebView=findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.setVerticalScrollbarOverlay(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http://callback")) {
                    view.loadUrl(url);
                } else {
                    try {
                        //LogUtil.i(url);

                        //转utf-8编码
                        String decode = URLDecoder.decode(url, "UTF-8");
                        //LogUtil.i(decode);

                        //转uri，然后根据key取值
                        Uri uri = Uri.parse(decode);
                        String latng = uri.getQueryParameter("latng");//纬度在前，经度在后，以逗号分隔
                        String[] split = latng.split(",");
                        lat = Float.parseFloat(split[0]);//纬度
                        lng = Float.parseFloat(split[1]);//经度
                        address = uri.getQueryParameter("addr");//地址

                        //LogUtil.i(uri.getQueryParameter("addr"));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
        mWebView.loadUrl(mUrl);
    }
}
