package com.linhlee.vidientu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhlee.vidientu.MyApplication;
import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListCardVerticalAdapter;
import com.linhlee.vidientu.models.CardObject;
import com.linhlee.vidientu.models.CardRequest;
import com.linhlee.vidientu.retrofit.IRetrofitAPI;
import com.linhlee.vidientu.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 10/18/2017.
 */

public class ListCardActivity extends BaseActivity implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;

    private ImageView backButton;
    private ListView listCardView;
    private ListCardVerticalAdapter adapter;
    private ArrayList<CardObject> listCard;
    private String channel;

    private Call<CardRequest> getCardInfoAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_list_card;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);

        backButton = (ImageView) findViewById(R.id.back_btn);
        listCardView = (ListView) findViewById(R.id.list_card);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            channel = extras.getString("channel");
        }

        listCard = new ArrayList<>();
        getListCard();

        adapter = new ListCardVerticalAdapter(this, listCard);

        listCardView.setAdapter(adapter);
        listCardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListCardActivity.this, PaidCardActivity.class);
                i.putExtra("telco", listCard.get(position).getBankName());
                startActivity(i);
            }
        });

        Constant.increaseHitArea(backButton);
        backButton.setOnClickListener(this);
    }

    private void getListCard() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("channel", channel);

        getCardInfoAPI = mRetrofitAPI.getCardInfo(body);
        getCardInfoAPI.enqueue(new Callback<CardRequest>() {
            @Override
            public void onResponse(Call<CardRequest> call, Response<CardRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listCard.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardRequest> call, Throwable t) {
                Toast.makeText(ListCardActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
