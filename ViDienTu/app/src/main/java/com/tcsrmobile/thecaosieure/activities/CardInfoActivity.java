package com.tcsrmobile.thecaosieure.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.utils.Constant;

/**
 * Created by Admin on 10/31/2017.
 */

public class CardInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private TextView textContent;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_card_info;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        textContent = (TextView) findViewById(R.id.text_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textContent.setText(extras.getString("card_info"));
        }

        Constant.increaseHitArea(backButton);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
