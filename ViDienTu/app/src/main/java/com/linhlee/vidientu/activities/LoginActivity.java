package com.linhlee.vidientu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.models.UserRequest;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 4/14/2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;

    private EditText editUsername;
    private EditText editPass;
    private Button loginButton;
    private TextView forgotPassButton;
    private TextView registerButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        app = (MyApplication) getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();

        editUsername = (EditText) findViewById(R.id.edit_username);
        editPass = (EditText) findViewById(R.id.edit_pass);
        loginButton = (Button) findViewById(R.id.login);
        forgotPassButton = (TextView) findViewById(R.id.forgot_pass_button);
        registerButton = (TextView) findViewById(R.id.register_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        loginButton.setOnClickListener(this);
        forgotPassButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                HashMap<String, Object> body = new HashMap<>();
                body.put("username", editUsername.getText().toString());
                body.put("password", editPass.getText().toString());
                body.put("deviceID", "dghwt923bgbo");

                Call<UserRequest> callLogin = mRetrofitAPI.login(body);
                callLogin.enqueue(new Callback<UserRequest>() {
                    @Override
                    public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                        if (errorCode == 1) {
                            User user = response.body().getData();
                            String jsonUser = mGson.toJson(user);

                            sharedPreferences.edit().putString(Constant.USER_INFO, jsonUser).apply();
                            sharedPreferences.edit().putBoolean(Constant.IS_LOGIN, true).apply();

                            finish();
                            Intent i = new Intent(Constant.LOGIN_SUCCESS);
                            sendBroadcast(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRequest> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.forgot_pass_button:
                startActivity(ForgotPassActivity.class);
                break;
            case R.id.register_button:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}
