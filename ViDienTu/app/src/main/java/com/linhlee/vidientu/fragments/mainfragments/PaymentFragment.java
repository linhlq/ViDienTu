package com.linhlee.vidientu.fragments.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.activities.DepositActivity;
import com.linhlee.vidientu.activities.PhoneRechargeActivity;
import com.linhlee.vidientu.activities.WithdrawActivity;
import com.linhlee.vidientu.adapters.ListFunctionAdapter;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.models.MenuObject;
import com.linhlee.vidientu.utils.Constant;
import com.linhlee.vidientu.utils.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class PaymentFragment extends BaseFragment {
    private RecyclerView listPaymentView;
    private ListFunctionAdapter adapter;
    private ArrayList<MenuObject> listPayment;

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
        listPaymentView = (RecyclerView) rootView.findViewById(R.id.list_payment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listPayment = new ArrayList<>();
        listPayment.add(new MenuObject(R.mipmap.ic_transfer_money, getActivity().getResources().getString(R.string.chuyen_tien)));
        listPayment.add(new MenuObject(R.mipmap.ic_nap_phone_card, getActivity().getResources().getString(R.string.nap_dien_thoai)));
        listPayment.add(new MenuObject(R.mipmap.ic_mua_phone_card, getActivity().getResources().getString(R.string.mua_the_dien_thoai)));
        listPayment.add(new MenuObject(R.mipmap.ic_doi_the_cao_green, getActivity().getResources().getString(R.string.doi_the_cao)));
        listPayment.add(new MenuObject(R.mipmap.ic_visa, getActivity().getResources().getString(R.string.visa)));
        listPayment.add(new MenuObject(R.mipmap.ic_deposit, getActivity().getResources().getString(R.string.nap_tien)));
        listPayment.add(new MenuObject(R.mipmap.ic_withdraw, getActivity().getResources().getString(R.string.rut_tien)));
        listPayment.add(new MenuObject(R.mipmap.ic_thanh_toan_hoa_don, getActivity().getResources().getString(R.string.thanh_toan_hoa_don)));
        listPayment.add(new MenuObject(R.mipmap.ic_diem_thanh_toan_green, getActivity().getResources().getString(R.string.diem_thanh_toan)));

        adapter = new ListFunctionAdapter(getActivity(), listPayment, R.layout.item_function_payment, new ListFunctionAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {
                switch (position) {
                    case 0:
                        Intent i = new Intent(Constant.GOTO_TRANSFER);
                        getActivity().sendBroadcast(i);
                        break;
                    case 1:
                        startActivity(PhoneRechargeActivity.class);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        startActivity(DepositActivity.class);
                        break;
                    case 6:
                        startActivity(WithdrawActivity.class);
                        break;
                    case 7:
                        break;
                    case 8:
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
