package com.linhlee.vidientu.fragments.personalfragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class PasswordFragment extends BaseFragment {

    public static PasswordFragment newInstance() {

        Bundle args = new Bundle();

        PasswordFragment fragment = new PasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_password;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
