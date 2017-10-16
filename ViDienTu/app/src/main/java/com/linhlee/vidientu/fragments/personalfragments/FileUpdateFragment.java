package com.linhlee.vidientu.fragments.personalfragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.models.UserRequest;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 4/25/2017.
 */

public class FileUpdateFragment extends BaseFragment implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private EditText editFullName;
    private EditText editEmail;
    private EditText editIdentity;
    private EditText editDateBirth;
    private EditText editAddress;
    private RadioButton male;
    private RadioButton female;
    private ImageView pickDate;
    private Button updateButton;

    public static FileUpdateFragment newInstance() {

        Bundle args = new Bundle();

        FileUpdateFragment fragment = new FileUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_file_update;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        app = (MyApplication) getActivity().getApplication();
        mGson = app.getGson();
        mRetrofit = app.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        editFullName = (EditText) rootView.findViewById(R.id.edit_fullname);
        editEmail = (EditText) rootView.findViewById(R.id.edit_email);
        editIdentity = (EditText) rootView.findViewById(R.id.edit_identity);
        editDateBirth = (EditText) rootView.findViewById(R.id.edit_datebirth);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        male = (RadioButton) rootView.findViewById(R.id.male);
        female = (RadioButton) rootView.findViewById(R.id.female);
        pickDate = (ImageView) rootView.findViewById(R.id.pick_date);
        updateButton = (Button) rootView.findViewById(R.id.update_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        editFullName.setText(user.getFullname());
        editEmail.setText(user.getEmail());
        editIdentity.setText(user.getIdentityNumber());
        editDateBirth.setText(user.getDateBirth());
        editAddress.setText(user.getAddress());
        if (user.getSex().equals("MALE")) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }

        pickDate.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                HashMap<String, Object> body = new HashMap<>();
                body.put("fullname", editFullName.getText().toString());
                body.put("email", editEmail.getText().toString());
                body.put("cmt", editIdentity.getText().toString());
                body.put("ngaysinh", editDateBirth.getText().toString());
                body.put("address", editAddress.getText().toString());
                if (male.isChecked()) {
                    body.put("sex", 1);
                } else {
                    body.put("sex", 0);
                }

                Call<UserRequest> callUpdate = mRetrofitAPI.changeProfile(user.getToken(), body);
                callUpdate.enqueue(new Callback<UserRequest>() {
                    @Override
                    public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                        int errorCode = response.body().getErrorCode();
                        String msg = response.body().getMsg();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        if (errorCode == 1) {
                            User user = response.body().getData();
                            String jsonUser = mGson.toJson(user);

                            sharedPreferences.edit().putString(Constant.USER_INFO, jsonUser).apply();

                            Intent i = new Intent(Constant.CHANGE_FILE_FRAGMENT);
                            i.putExtra("command", 1);
                            getActivity().sendBroadcast(i);
                        } else {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRequest> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.pick_date:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String dateString = Constant.formatTime(dayOfMonth) + "/" + Constant.formatTime(month + 1) + "/" + year;
                        editDateBirth.setText(dateString);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.show();
                break;
        }
    }
}
