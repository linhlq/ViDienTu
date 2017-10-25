package com.tcsrmobile.thecaosieure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcsrmobile.thecaosieure.MyApplication;
import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.adapters.ListNewsAllAdapter;
import com.tcsrmobile.thecaosieure.models.NewsObject;
import com.tcsrmobile.thecaosieure.models.NewsRequest;
import com.tcsrmobile.thecaosieure.retrofit.IRetrofitAPI;
import com.tcsrmobile.thecaosieure.utils.Constant;
import com.tcsrmobile.thecaosieure.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 4/14/2017.
 */

public class NewsActivity extends BaseActivity implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;

    private ImageView backButton;
    private RecyclerView listNewsView;
    private ListNewsAllAdapter listNewsAdapter;
    private ArrayList<NewsObject> listNews;

    private Call<NewsRequest> getNewsAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);

        backButton = (ImageView) findViewById(R.id.back_btn);
        listNewsView = (RecyclerView) findViewById(R.id.list_news);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listNews = new ArrayList<>();
        getListNews();

        listNewsAdapter = new ListNewsAllAdapter(this, listNews, new ListNewsAllAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {
                Intent i = new Intent(NewsActivity.this, DetailActivity.class);
                i.putExtra("title", listNews.get(position).getTitle());
                i.putExtra("url", listNews.get(position).getUrl());
                startActivity(i);
            }
        });
        listNewsAdapter.setHasStableIds(true);

        listNewsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listNewsView.addItemDecoration(new SimpleDividerItemDecoration(this));
        listNewsView.setAdapter(listNewsAdapter);

        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    private void getListNews() {
        getNewsAPI = mRetrofitAPI.getArticle();
        getNewsAPI.enqueue(new Callback<NewsRequest>() {
            @Override
            public void onResponse(Call<NewsRequest> call, Response<NewsRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    listNews.addAll(response.body().getData());
                    listNewsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NewsActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsRequest> call, Throwable t) {
                Toast.makeText(NewsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
