package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListCardAdapter;
import com.linhlee.vidientu.models.CardObject;
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
    private ArrayList<CardObject> listPhoneCard;
    private TextView textAmount;
    private ImageView minusButton;
    private ImageView plusButton;

    private int amount = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_buy_phone_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        listPhoneCardView = (RecyclerView) findViewById(R.id.list_phone_card);
        textAmount = (TextView) findViewById(R.id.text_amount);
        minusButton = (ImageView) findViewById(R.id.button_minus);
        plusButton = (ImageView) findViewById(R.id.button_plus);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listPhoneCard = new ArrayList<>();

        adapter = new ListCardAdapter(this, listPhoneCard, new ListCardAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {

            }
        });
        adapter.setHasStableIds(true);

        listPhoneCardView.setLayoutManager(new GridLayoutManager(this, 3));
        listPhoneCardView.addItemDecoration(new GridSpacingItemDecoration(3, Constant.convertDpIntoPixels(20, this), true));
        listPhoneCardView.setAdapter(adapter);

        textAmount.setText(amount + "");

        Constant.increaseHitArea(backButton);
        Constant.increaseHitArea(minusButton);
        Constant.increaseHitArea(plusButton);

        backButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.button_minus:
                if (amount > 1) {
                    amount--;
                    textAmount.setText(amount + "");
                }
                break;
            case R.id.button_plus:
                if (amount < 99) {
                    amount++;
                    textAmount.setText(amount + "");
                }
                break;
        }
    }
}
