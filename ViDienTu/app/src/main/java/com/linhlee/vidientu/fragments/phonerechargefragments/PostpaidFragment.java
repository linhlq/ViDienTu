package com.linhlee.vidientu.fragments.phonerechargefragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;

/**
 * Created by lequy on 4/26/2017.
 */

public class PostpaidFragment extends BaseFragment {

    public static PostpaidFragment newInstance() {

        Bundle args = new Bundle();

        PostpaidFragment fragment = new PostpaidFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_postpaid;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
