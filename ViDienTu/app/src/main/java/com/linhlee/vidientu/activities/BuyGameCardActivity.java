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

public class BuyGameCardActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private RecyclerView listGameCardView;
    private ListCardAdapter adapter;
    private ArrayList<Integer> listGameCard;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_buy_game_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        listGameCardView = (RecyclerView) findViewById(R.id.list_game_card);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listGameCard = new ArrayList<>();
        listGameCard.add(R.mipmap.img_garena);
        listGameCard.add(R.mipmap.img_gate);
        listGameCard.add(R.mipmap.img_zing);
        listGameCard.add(R.mipmap.img_vcoin);
        listGameCard.add(R.mipmap.img_oncash);
        listGameCard.add(R.mipmap.img_muathe247);

        adapter = new ListCardAdapter(this, listGameCard, new ListCardAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {

            }
        });
        adapter.setHasStableIds(true);

        listGameCardView.setLayoutManager(new GridLayoutManager(this, 3));
        listGameCardView.addItemDecoration(new GridSpacingItemDecoration(3, Constant.convertDpIntoPixels(20, this), true));
        listGameCardView.setAdapter(adapter);

        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
