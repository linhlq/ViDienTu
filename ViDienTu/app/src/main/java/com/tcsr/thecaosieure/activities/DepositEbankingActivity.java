package com.tcsr.thecaosieure.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcsr.thecaosieure.MyApplication;
import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.models.User;
import com.tcsr.thecaosieure.utils.Constant;

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(DepositEbankingActivity.this, description, Toast.LENGTH_SHORT).show();
            }
        });

        if (user != null) {
            webView.loadUrl(Constant.EBANKING_URL + user.getToken());
        } else {
            Toast.makeText(this, "Bạn chưa đăng nhập, vui lòng đăng nhập để có thể sử dụng tính năng này", Toast.LENGTH_SHORT).show();
        }
    }
}
