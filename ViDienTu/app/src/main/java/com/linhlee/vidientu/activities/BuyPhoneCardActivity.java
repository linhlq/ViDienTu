package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListCardAdapter;
import com.linhlee.vidientu.utils.Constant;
import com.linhlee.vidientu.utils.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by lequy on 4/27/2017.
 */

public class BuyPhoneCardActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private RecyclerView listPhoneCardView;
    private ListCardAdapter adapter;
    private ArrayList<Integer> listPhoneCard;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_buy_phone_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        listPhoneCardView = (RecyclerView) findViewById(R.id.list_phone_card);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listPhoneCard = new ArrayList<>();
        listPhoneCard.add(R.mipmap.img_mobiphone);
        listPhoneCard.add(R.mipmap.img_viettel);
        listPhoneCard.add(R.mipmap.img_vinaphone);
        listPhoneCard.add(R.mipmap.img_sphone);
        listPhoneCard.add(R.mipmap.img_vnmobile);
        listPhoneCard.add(R.mipmap.img_gmobile);

        adapter = new ListCardAdapter(this, listPhoneCard, new ListCardAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {

            }
        });
        adapter.setHasStableIds(true);

        listPhoneCardView.setLayoutManager(new GridLayoutManager(this, 3));
        listPhoneCardView.addItemDecoration(new GridSpacingItemDecoration(3, Constant.convertDpIntoPixels(20, this), true));
        listPhoneCardView.setAdapter(adapter);

        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
