package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by Linh Lee on 4/6/2017.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        }, 3000);
    }
}
