package com.linhlee.vidientu.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListCardAdapter;
import com.linhlee.vidientu.models.CardObject;
import com.linhlee.vidientu.models.CardRequest;
import com.linhlee.vidientu.models.OtherRequest;
import com.linhlee.vidientu.models.User;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;
import com.linhlee.vidientu.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 4/27/2017.
 */

public class BuyPhoneCardActivity extends BaseActivity implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;
    private SharedPreferences sharedPreferences;
    private User user;

    private ImageView backButton;
    private RecyclerView listPhoneCardView;
    private ListCardAdapter adapter;
    private ArrayList<CardObject> listPhoneCard;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> listPrice;
    private ArrayList<String> listInfo;
    private TextView textAmount;
    private ImageView minusButton;
    private ImageView plusButton;
    private TextView textThanhToan;
    private EditText editMk2;
    private Button continueButton;
    private TextView viewCard;
    private int curPos = 0;
    private int amount = 1;

    private Call<CardRequest> getCardInfoAPI;
    private Call<OtherRequest> buyCardAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_buy_phone_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);
        sharedPreferences = MyApplication.getSharedPreferences();
        user = mGson.fromJson(sharedPreferences.getString(Constant.USER_INFO, ""), User.class);

        backButton = (ImageView) findViewById(R.id.back_btn);
        listPhoneCardView = (RecyclerView) findViewById(R.id.list_phone_card);
        spinner = (Spinner) findViewById(R.id.spinner);
        textAmount = (TextView) findViewById(R.id.text_amount);
        minusButton = (ImageView) findViewById(R.id.button_minus);
        plusButton = (ImageView) findViewById(R.id.button_plus);
        textThanhToan = (TextView) findViewById(R.id.text_thanh_toan);
        editMk2 = (EditText) findViewById(R.id.edit_mk2);
        continueButton = (Button) findViewById(R.id.continue_button);
        viewCard = (TextView) findViewById(R.id.view_card);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listPrice = new ArrayList<>();
        listInfo = new ArrayList<>();
        listPhoneCard = new ArrayList<>();
        getListCard();

        adapter = new ListCardAdapter(this, listPhoneCard, new ListCardAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {
                curPos = position;

                listInfo.removeAll(listInfo);
                listPrice.removeAll(listPrice);
                listInfo.addAll(Arrays.asList(listPhoneCard.get(position).getInfo().split("\\|")));
                for (int i = 0; i < listInfo.size(); i++) {
                    listPrice.add(listInfo.get(i) + " VNĐ");
                }
                spinnerAdapter.notifyDataSetChanged();
                spinner.setSelection(0);

                textThanhToan.setText(String.format("%.0f", getThanhToan(0)) + " VNĐ");
            }
        });
        adapter.setHasStableIds(true);
        adapter.setDPT(false);

        listPhoneCardView.setLayoutManager(new GridLayoutManager(this, 3));
        listPhoneCardView.addItemDecoration(new GridSpacingItemDecoration(3, Constant.convertDpIntoPixels(20, this), true));
        listPhoneCardView.setAdapter(adapter);

        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, listPrice);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textThanhToan.setText(String.format("%.0f", getThanhToan(position)) + " VNĐ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textAmount.setText(amount + "");

        Constant.increaseHitArea(backButton);
        Constant.increaseHitArea(minusButton);
        Constant.increaseHitArea(plusButton);
        Constant.increaseHitArea(viewCard);

        backButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        viewCard.setOnClickListener(this);
    }

    private void getListCard() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("channel", "telco");

        getCardInfoAPI = mRetrofitAPI.getCardInfo(body);
        getCardInfoAPI.enqueue(new Callback<CardRequest>() {
            @Override
            public void onResponse(Call<CardRequest> call, Response<CardRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listPhoneCard.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();

                    listInfo.addAll(Arrays.asList(listPhoneCard.get(0).getInfo().split("\\|")));
                    for (int i = 0; i < listInfo.size(); i++) {
                        listPrice.add(listInfo.get(i) + " VNĐ");
                    }
                    spinnerAdapter.notifyDataSetChanged();

                    textThanhToan.setText(String.format("%.0f", getThanhToan(0)) + " VNĐ");
                } else {
                    Toast.makeText(BuyPhoneCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardRequest> call, Throwable t) {
                Toast.makeText(BuyPhoneCardActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buyCard() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("product", listPhoneCard.get(curPos).getBankName());
        body.put("menhgia", listInfo.get(spinner.getSelectedItemPosition()));
        body.put("soluong", amount);
        body.put("mk2", editMk2.getText().toString());

        buyCardAPI = mRetrofitAPI.buyCard(user.getToken(), body);
        buyCardAPI.enqueue(new Callback<OtherRequest>() {
            @Override
            public void onResponse(Call<OtherRequest> call, Response<OtherRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();
                Toast.makeText(BuyPhoneCardActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OtherRequest> call, Throwable t) {
                Toast.makeText(BuyPhoneCardActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private float getThanhToan(int spinnerPos) {
        return Integer.valueOf(listInfo.get(spinnerPos)) * listPhoneCard.get(curPos).getDiscount() / 100;
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
            case R.id.continue_button:
                buyCard();
                break;
            case R.id.view_card:
                Intent i = new Intent(this, ListCardActivity.class);
                i.putExtra("channel", "telco");
                startActivity(i);
                break;
        }
    }
}
