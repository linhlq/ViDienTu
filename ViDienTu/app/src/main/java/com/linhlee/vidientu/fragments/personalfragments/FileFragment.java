package com.linhlee.vidientu.fragments.personalfragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class FileFragment extends BaseFragment {

    public static FileFragment newInstance() {

        Bundle args = new Bundle();

        FileFragment fragment = new FileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_file;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
