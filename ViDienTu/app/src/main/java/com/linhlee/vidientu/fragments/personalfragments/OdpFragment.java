package com.linhlee.vidientu.fragments.personalfragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;
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
public class OdpFragment extends BaseFragment implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private CheckBox checkBox;
    private EditText editOdp;
    private Button buttonContinue;
    private Button buttonResend;

    private Call<OtherRequest> resendOdpAPI;
    private Call<OtherRequest> chooseOdpAPI;

    public static OdpFragment newInstance() {

        Bundle args = new Bundle();

        OdpFragment fragment = new OdpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_odp;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = MyApplication.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        checkBox = (CheckBox) rootView.findViewById(R.id.check_box);
        editOdp = (EditText) rootView.findViewById(R.id.edit_odp);
        buttonContinue = (Button) rootView.findViewById(R.id.button_continue);
        buttonResend = (Button) rootView.findViewById(R.id.button_resend);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (user != null) {
            if (user.getReceive_otp().equals("M")) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
        }

        buttonContinue.setOnClickListener(this);
        buttonResend.setOnClickListener(this);
    }

    private void chooseOdp() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("isUse", checkBox.isChecked());
        body.put("odp", editOdp.getText().toString());

        chooseOdpAPI = mRetrofitAPI.chooseODP(user.getToken(), body);
        chooseOdpAPI.enqueue(new Callback<OtherRequest>() {
            @Override
            public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OtherRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendOdp() {
        resendOdpAPI = mRetrofitAPI.resendODP(user.getToken());
        resendOdpAPI.enqueue(new Callback<OtherRequest>() {
            @Override
            public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OtherRequest> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_continue:
                chooseOdp();
                break;
            case R.id.button_resend:
                resendOdp();
                break;
        }
    }
}
