package com.tcsr.thecaosieure.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.utils.Constant;

/**
 * Created by lequy on 10/18/2017.
 */

public class TransDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backBtn;
    private TextView titleText;
    private WebView webView;
    private String content = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_trans_detail;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backBtn = (ImageView) findViewById(R.id.back_btn);
        titleText = (TextView) findViewById(R.id.title_text);
        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            content = extras.getString("content");
        }

        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);

        Constant.increaseHitArea(backBtn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
