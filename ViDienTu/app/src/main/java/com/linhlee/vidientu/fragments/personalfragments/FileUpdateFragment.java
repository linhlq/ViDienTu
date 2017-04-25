package com.linhlee.vidientu.fragments.personalfragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.utils.Constant;

/**
 * Created by lequy on 4/25/2017.
 */

public class FileUpdateFragment extends BaseFragment implements View.OnClickListener {
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
        updateButton = (Button) rootView.findViewById(R.id.update_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Constant.CHANGE_FILE_FRAGMENT);
        i.putExtra("command", 1);
        getActivity().sendBroadcast(i);
    }
}
