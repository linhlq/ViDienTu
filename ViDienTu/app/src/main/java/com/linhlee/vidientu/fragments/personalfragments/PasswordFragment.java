package com.linhlee.vidientu.fragments.personalfragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.dialogs.NoticeDialog;
import com.linhlee.vidientu.fragments.BaseFragment;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class PasswordFragment extends BaseFragment implements View.OnClickListener {
    private RadioButton pass1;
    private RadioButton pass2;
    private EditText currentPass;
    private EditText newPass;
    private EditText retypeNewPass;
    private Button changePassButton;

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
        pass1 = (RadioButton) rootView.findViewById(R.id.pass_1);
        pass2 = (RadioButton) rootView.findViewById(R.id.pass_2);
        currentPass = (EditText) rootView.findViewById(R.id.current_pass);
        newPass = (EditText) rootView.findViewById(R.id.new_pass);
        retypeNewPass = (EditText) rootView.findViewById(R.id.retype_new_pass);
        changePassButton = (Button) rootView.findViewById(R.id.change_pass_button);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        changePassButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_pass_button:
                NoticeDialog dialog = new NoticeDialog(getActivity(), "Tao thích");
                dialog.show();
                break;
        }
    }
}
