package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linhlee.vidientu.R;

/**
 * Created by lequy on 4/14/2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView forgotPassButton;
    private TextView registerButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        forgotPassButton = (TextView) findViewById(R.id.forgot_pass_button);
        registerButton = (TextView) findViewById(R.id.register_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        forgotPassButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_pass_button:
                startActivity(ForgotPassActivity.class);
                break;
            case R.id.register_button:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}
