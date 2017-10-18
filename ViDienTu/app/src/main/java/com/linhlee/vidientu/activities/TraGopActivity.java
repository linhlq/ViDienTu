package com.linhlee.vidientu.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.models.CardObject;
import com.linhlee.vidientu.models.CardRequest;
import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 7/6/2017.
 */

public class TraGopActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private ImageView backButton;
    private EditText editMoneyAmount;
    private Spinner spinnerHd;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listHdName;
    private ArrayList<CardObject> listHd;
    private EditText editMaHd;
    private EditText editPass;
    private EditText editDes;
    private Button continueButton;

    private Call<CardRequest> getCardInfoAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_tra_gop;
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
        spinnerHd = (Spinner) findViewById(R.id.spinner_hd);
        editMaHd = (EditText) findViewById(R.id.edit_ma_hd);
        editPass = (EditText) findViewById(R.id.edit_pass);
        editDes = (EditText) findViewById(R.id.edit_des);
        continueButton = (Button) findViewById(R.id.button_continue);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listHdName = new ArrayList<>();
        getListHd();

        adapter = new ArrayAdapter<>(this, R.layout.item_spinner, listHdName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHd.setAdapter(adapter);
        spinnerHd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Constant.increaseHitArea(backButton);
        backButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
    }

    private void getListHd() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("channel", "sale");

        getCardInfoAPI = mRetrofitAPI.getCardInfo(body);
        getCardInfoAPI.enqueue(new Callback<CardRequest>() {
            @Override
            public void onResponse(Call<CardRequest> call, Response<CardRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listHd = response.body().getData();

                    for (int i = 0; i < listHd.size(); i++) {
                        listHdName.add(listHd.get(i).getBankName());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TraGopActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardRequest> call, Throwable t) {
                Toast.makeText(TraGopActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                String phoneNumber = "";
                if (user != null) {
                    token = user.getToken();
                    fullname = user.getFullname();
                    phoneNumber = user.getMobile();
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("partner", listHdName.get(spinnerHd.getSelectedItemPosition()));
                body.put("mk2", editPass.getText().toString());
                body.put("fullname", fullname);
                body.put("maKH_HD", editMaHd.getText().toString());
                body.put("payAmount", editMoneyAmount.getText().toString());
                body.put("ghichu", editDes.getText().toString());
                body.put("sodienthoai", phoneNumber);

                Call<OtherRequest> postSaleHD = mRetrofitAPI.postSaleHD(token, body);
                postSaleHD.enqueue(new Callback<OtherRequest>() {
                    @Override
                    public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(TraGopActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<OtherRequest> call, Throwable t) {
                        Toast.makeText(TraGopActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
