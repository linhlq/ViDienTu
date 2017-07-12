package com.linhlee.vidientu.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 7/5/2017.
 */

public class DepositCardActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private ImageView backButton;
    private EditText editTypeCard;
    private EditText editSerie;
    private EditText editMaThe;
    private Button continueButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_deposit_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        app = (MyApplication) getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        backButton = (ImageView) findViewById(R.id.back_btn);
        editTypeCard = (EditText) findViewById(R.id.edit_type_card);
        editSerie = (EditText) findViewById(R.id.edit_serie);
        editMaThe = (EditText) findViewById(R.id.edit_ma_the);
        continueButton = (Button) findViewById(R.id.button_continue);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.button_continue:
                String token = "";
                if (user != null) {
                    token = user.getToken();
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("loaithe", editTypeCard.getText().toString());
                body.put("serial", editSerie.getText().toString());
                body.put("mathe", editMaThe.getText().toString());

                Call<OtherRequest> cardCharge = mRetrofitAPI.cardCharge(token, body);
                cardCharge.enqueue(new Callback<OtherRequest>() {
                    @Override
                    public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(DepositCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OtherRequest> call, Throwable t) {
                        Toast.makeText(DepositCardActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
