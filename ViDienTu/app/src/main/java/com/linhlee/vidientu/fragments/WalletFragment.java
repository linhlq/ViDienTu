package com.linhlee.vidientu.fragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class WalletFragment extends BaseFragment {

    public static WalletFragment newInstance() {

        Bundle args = new Bundle();

        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
