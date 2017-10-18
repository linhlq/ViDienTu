package com.linhlee.vidientu.fragments.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.models.FullNameRequest;
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
 * Created by Linh Lee on 4/9/2017.
 */
public class TransferFragment extends BaseFragment implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private EditText editMoneyAmount;
    private EditText editReceiverName;
    private EditText editConfirmPass;
    private EditText editDes;
    private Button continueButton;
    private Handler handler;

    private long delay = 1000;
    private long last_text_edit = 0;

    private Call<FullNameRequest> getFullnameAPI;

    public static TransferFragment newInstance() {

        Bundle args = new Bundle();

        TransferFragment fragment = new TransferFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        app = (MyApplication) getActivity().getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        editMoneyAmount = (EditText) rootView.findViewById(R.id.edit_money_amount);
        editReceiverName = (EditText) rootView.findViewById(R.id.edit_receiver_name);
        editConfirmPass = (EditText) rootView.findViewById(R.id.edit_confirm_pass);
        editDes = (EditText) rootView.findViewById(R.id.edit_des);
        continueButton = (Button) rootView.findViewById(R.id.button_continue);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        handler = new Handler();

        editReceiverName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        });

        continueButton.setOnClickListener(this);
    }

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                getFullName();
            }
        }
    };

    private void getFullName() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("username", editReceiverName.getText().toString());

        getFullnameAPI = mRetrofitAPI.getFullName(body);
        getFullnameAPI.enqueue(new Callback<FullNameRequest>() {
            @Override
            public void onResponse(Call<FullNameRequest> call, Response<FullNameRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    if (response.body().getData() != null) {
                        String fullName = response.body().getData();
                        editDes.setText("Chuyển tiền cho " + fullName + " với số tiền " + editMoneyAmount.getText().toString() + " VNĐ");
                    }
                } else {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FullNameRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_continue:
                HashMap<String, Object> body = new HashMap<>();
                body.put("sotien", editMoneyAmount.getText().toString());
                body.put("userReceive", editReceiverName.getText().toString());
                body.put("mk2", editConfirmPass.getText().toString());
                body.put("mota", editDes.getText().toString());

                String token = "";
                if (user != null) {
                    token = user.getToken();
                }

                Call<OtherRequest> transferMoney = mRetrofitAPI.transferMoney(token, body);
                transferMoney.enqueue(new Callback<OtherRequest>() {
                    @Override
                    public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Constant.UPDATE_INFO);
                        getActivity().sendBroadcast(i);
                    }

                    @Override
                    public void onFailure(Call<OtherRequest> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
