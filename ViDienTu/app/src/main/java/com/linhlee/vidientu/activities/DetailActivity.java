package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.utils.Constant;

/**
 * Created by lequy on 4/14/2017.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private TextView titleText;
    private TextView contentText;
    private String title;
    private String content;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        titleText = (TextView) findViewById(R.id.title_text);
        contentText = (TextView) findViewById(R.id.content_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            content = extras.getString("content");
        }

        titleText.setText(title);
        contentText.setText(content);

        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
