package com.linhlee.vidientu.fragments.personalfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.utils.Constant;

/**
 * Created by lequy on 4/25/2017.
 */

public class FileDisplayFragment extends BaseFragment implements View.OnClickListener {
    private MyApplication app;
    private Gson mGson;
    private SharedPreferences sharedPreferences;
    private User user;

    private TextView fullName;
    private TextView mobile;
    private TextView email;
    private TextView identityNumber;
    private TextView dateBirth;
    private TextView sex;
    private TextView address;
    private TextView updateButton;

    public static FileDisplayFragment newInstance() {

        Bundle args = new Bundle();

        FileDisplayFragment fragment = new FileDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_file_display;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        app = (MyApplication) getActivity().getApplication();
        mGson = app.getGson();
        sharedPreferences = app.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        fullName = (TextView) rootView.findViewById(R.id.full_name);
        mobile = (TextView) rootView.findViewById(R.id.mobile);
        email = (TextView) rootView.findViewById(R.id.email);
        identityNumber = (TextView) rootView.findViewById(R.id.identity_number);
        dateBirth = (TextView) rootView.findViewById(R.id.date_birth);
        sex = (TextView) rootView.findViewById(R.id.sex);
        address = (TextView) rootView.findViewById(R.id.address);
        updateButton = (TextView) rootView.findViewById(R.id.update_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (user != null) {
            fullName.setText(user.getFullname());
            mobile.setText(user.getMobile());
            email.setText(user.getEmail());
            identityNumber.setText(user.getIdentityNumber());
            dateBirth.setText(user.getDateBirth());
            if (user.getGioitinh() == 1) {
                sex.setText("Nam");
            } else {
                sex.setText("Nữ");
            }
            address.setText(user.getAddress());
        }

        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Constant.CHANGE_FILE_FRAGMENT);
        i.putExtra("command", 0);
        getActivity().sendBroadcast(i);
    }
}
