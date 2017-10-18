package com.linhlee.vidientu.activities;

import android.content.Intent;
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
import com.linhlee.vidientu.dialogs.LoadingDialog;
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
 * Created by lequy on 7/3/2017.
 */

public class WithdrawBankActivity extends BaseActivity implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private ImageView backButton;
    private EditText editMoneyAmount;
    private Spinner spinnerBank;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listBankName;
    private ArrayList<CardObject> listBank;
    private EditText editChiNhanh;
    private EditText editSoTk;
    private EditText editFullName;
    private EditText editPass;
    private Button continueButton;
    private LoadingDialog loadingDialog;

    private Call<CardRequest> getCardInfoAPI;
    private Call<OtherRequest> postAccountBankAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_withdraw_bank;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        app = (MyApplication) getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        loadingDialog = new LoadingDialog(this);

        backButton = (ImageView) findViewById(R.id.back_btn);
        editMoneyAmount = (EditText) findViewById(R.id.edit_money_amount);
        spinnerBank = (Spinner) findViewById(R.id.spinner_bank);
        editChiNhanh = (EditText) findViewById(R.id.edit_chi_nhanh);
        editSoTk = (EditText) findViewById(R.id.edit_so_tk);
        editFullName = (EditText) findViewById(R.id.edit_fullname);
        editPass = (EditText) findViewById(R.id.edit_pass);
        continueButton = (Button) findViewById(R.id.button_continue);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listBankName = new ArrayList<>();
        getListBank();

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, listBankName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(spinnerAdapter);
        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void getListBank() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("channel", "wdr");

        getCardInfoAPI = mRetrofitAPI.getCardInfo(body);
        getCardInfoAPI.enqueue(new Callback<CardRequest>() {
            @Override
            public void onResponse(Call<CardRequest> call, Response<CardRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listBank = response.body().getData();

                    for (int i = 0; i < listBank.size(); i++) {
                        listBankName.add(listBank.get(i).getBankName());
                    }
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(WithdrawBankActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardRequest> call, Throwable t) {
                Toast.makeText(WithdrawBankActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                if (user != null) {
                    token = user.getToken();
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("sotienrut", editMoneyAmount.getText().toString());
                body.put("bank", listBankName.get(spinnerBank.getSelectedItemPosition()));
                body.put("chinhanh", editChiNhanh.getText().toString());
                body.put("soTK", editSoTk.getText().toString());
                body.put("fullname", editFullName.getText().toString());
                body.put("mk2", editPass.getText().toString());

                loadingDialog.show();
                postAccountBankAPI = mRetrofitAPI.postAccountBank(token, body);
                postAccountBankAPI.enqueue(new Callback<OtherRequest>() {
                    @Override
                    public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(WithdrawBankActivity.this, msg, Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                        Intent i = new Intent(Constant.UPDATE_INFO);
                        sendBroadcast(i);
                    }

                    @Override
                    public void onFailure(Call<OtherRequest> call, Throwable t) {
                        Toast.makeText(WithdrawBankActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });
                break;
        }
    }
}
