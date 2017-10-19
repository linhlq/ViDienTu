package com.tcsr.thecaosieure.fragments.mainfragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tcsr.thecaosieure.MyApplication;
import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.activities.BuyGameCardActivity;
import com.tcsr.thecaosieure.activities.BuyPhoneCardActivity;
import com.tcsr.thecaosieure.activities.DepositActivity;
import com.tcsr.thecaosieure.activities.DepositCardActivity;
import com.tcsr.thecaosieure.activities.PhoneRechargeActivity;
import com.tcsr.thecaosieure.activities.TraGopActivity;
import com.tcsr.thecaosieure.activities.WithdrawActivity;
import com.tcsr.thecaosieure.adapters.ListFunctionAdapter;
import com.tcsr.thecaosieure.fragments.BaseFragment;
import com.tcsr.thecaosieure.models.MenuObject;
import com.tcsr.thecaosieure.utils.Constant;
import com.tcsr.thecaosieure.utils.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class PaymentFragment extends BaseFragment {
    private RecyclerView listPaymentView;
    private ListFunctionAdapter adapter;
    private ArrayList<MenuObject> listPayment;
    private SharedPreferences sharedPreferences;

    public static PaymentFragment newInstance() {

        Bundle args = new Bundle();

        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        sharedPreferences = MyApplication.getSharedPreferences();

        listPaymentView = (RecyclerView) rootView.findViewById(R.id.list_payment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listPayment = new ArrayList<>();
        listPayment.add(new MenuObject(R.drawable.ic_deposit_state, getActivity().getResources().getString(R.string.nap_tien)));
        listPayment.add(new MenuObject(R.drawable.ic_nap_phone_card_state, getActivity().getResources().getString(R.string.nap_dien_thoai)));
        listPayment.add(new MenuObject(R.drawable.ic_mua_phone_card_state, getActivity().getResources().getString(R.string.mua_the_dien_thoai)));
        listPayment.add(new MenuObject(R.drawable.ic_transfer_money_state, getActivity().getResources().getString(R.string.chuyen_tien)));
        listPayment.add(new MenuObject(R.drawable.ic_mua_the_game_state, getActivity().getResources().getString(R.string.mua_the_game)));
        listPayment.add(new MenuObject(R.drawable.ic_withdraw_state, getActivity().getResources().getString(R.string.rut_tien)));
        listPayment.add(new MenuObject(R.drawable.ic_diem_thanh_toan_state, getActivity().getResources().getString(R.string.diem_thanh_toan)));
        listPayment.add(new MenuObject(R.drawable.ic_doi_the_cao_state, getActivity().getResources().getString(R.string.doi_the_cao)));

        adapter = new ListFunctionAdapter(getActivity(), listPayment, R.layout.item_function_payment, new ListFunctionAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position) {
                    case 0:
                        startActivity(DepositActivity.class);
                        //Nap tien
                        break;
                    case 1:
                        startActivity(PhoneRechargeActivity.class);
                        //Nap tien dt
                        break;
                    case 2:
                        startActivity(BuyPhoneCardActivity.class);
                        //Mua the dt
                        break;
                    case 3:
                        Intent i = new Intent(Constant.GOTO_TRANSFER);
                        getActivity().sendBroadcast(i);
                        //Chuyen tien
                        break;
                    case 4:
                        startActivity(BuyGameCardActivity.class);
                        //Mua the game
                        break;
                    case 5:
                        startActivity(WithdrawActivity.class);
                        //Rut tien
                        break;
                    case 6:
                        startActivity(TraGopActivity.class);
                        //Diem thanh toan tra gop
                        break;
                    case 7:
                        startActivity(DepositCardActivity.class);
                        //Doi the cao thanh tien mat
                        break;
                }
            }
        });
        adapter.setHasStableIds(true);

        listPaymentView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listPaymentView.addItemDecoration(new GridSpacingItemDecoration(3, Constant.convertDpIntoPixels(40, getActivity()), false));
        listPaymentView.setAdapter(adapter);
    }
}
