package com.linhlee.vidientu.fragments.personalfragments;

import android.os.Bundle;
import android.view.View;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class AvatarFragment extends BaseFragment {

    public static AvatarFragment newInstance() {

        Bundle args = new Bundle();

        AvatarFragment fragment = new AvatarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_avatar;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
