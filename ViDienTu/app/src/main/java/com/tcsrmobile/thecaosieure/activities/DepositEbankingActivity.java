package com.tcsrmobile.thecaosieure.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcsrmobile.thecaosieure.MyApplication;
import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.models.User;
import com.tcsrmobile.thecaosieure.utils.Constant;

/**
 * Created by lequy on 10/18/2017.
 */

public class DepositEbankingActivity extends BaseActivity {
    private Gson mGson;
    private SharedPreferences sharedPreferences;
    private User user;

    private WebView webView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_deposit_ebanking;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mGson = MyApplication.getGson();
        sharedPreferences = MyApplication.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        if (user != null) {
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(Constant.EBANKING_URL + user.getToken());
        } else {
            Toast.makeText(this, "Bạn chưa đăng nhập, vui lòng đăng nhập để có thể sử dụng tính năng này", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
