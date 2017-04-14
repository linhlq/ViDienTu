package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.utils.Constant;

/**
 * Created by lequy on 4/14/2017.
 */

public class DepositActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_deposit;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
