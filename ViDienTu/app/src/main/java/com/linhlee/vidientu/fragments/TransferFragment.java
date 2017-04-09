package com.linhlee.vidientu.fragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class TransferFragment extends BaseFragment {

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
