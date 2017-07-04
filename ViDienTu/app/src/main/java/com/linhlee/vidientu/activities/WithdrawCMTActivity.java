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
 * Created by lequy on 7/3/2017.
 */

public class WithdrawCMTActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private ImageView backButton;
    private EditText editMoneyAmount;
    private EditText editBankName;
    private EditText editChiNhanh;
    private EditText editIdentity;
    private EditText editPlace;
    private EditText editDate;
    private EditText editPhone;
    private EditText editPass;
    private Button continueButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_withdraw_cmt;
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
        editMoneyAmount = (EditText) findViewById(R.id.edit_money_amount);
        editBankName = (EditText) findViewById(R.id.edit_bank_name);
        editChiNhanh = (EditText) findViewById(R.id.edit_chi_nhanh);
        editIdentity = (EditText) findViewById(R.id.edit_identity);
        editPlace = (EditText) findViewById(R.id.edit_place);
        editDate = (EditText) findViewById(R.id.edit_date);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editPass = (EditText) findViewById(R.id.edit_pass);
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
                String fullname = "";
                if (user != null) {
                    token = user.getToken();
                    fullname = user.getFullname();
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("sotienrut", editMoneyAmount.getText().toString());
                body.put("chinhanh", editChiNhanh.getText().toString());
                body.put("sodienthoai", editPhone.getText().toString());
                body.put("noicap", editPlace.getText().toString());
                body.put("ngaycap", editDate.getText().toString());
                body.put("fullname", fullname);
                body.put("mk2", editPass.getText().toString());

                Call<OtherRequest> postCMTdraw = mRetrofitAPI.postCMTdraw(token, body);
                postCMTdraw.enqueue(new Callback<OtherRequest>() {
                    @Override
                    public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(WithdrawCMTActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OtherRequest> call, Throwable t) {
                        Toast.makeText(WithdrawCMTActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
