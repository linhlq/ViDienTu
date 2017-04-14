package com.linhlee.vidientu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.ListNewsAllAdapter;
import com.linhlee.vidientu.models.NewsObject;
import com.linhlee.vidientu.utils.Constant;
import com.linhlee.vidientu.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by lequy on 4/14/2017.
 */

public class NewsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private RecyclerView listNewsView;
    private ListNewsAllAdapter listNewsAdapter;
    private ArrayList<NewsObject> listNews;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        listNewsView = (RecyclerView) findViewById(R.id.list_news);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listNews = new ArrayList<>();
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));
        listNews.add(new NewsObject(R.mipmap.item_news_sample, "[Từ 16/04/2016] Thay đổi chính sách đổi thẻ cào sang tiền mặt", "Bắt đầu từ 16/04/2016, chúng tôi sẽ có chính sách mới cho việc quy đổi thẻ cào sang tiền mặt"));

        listNewsAdapter = new ListNewsAllAdapter(this, listNews, new ListNewsAllAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {
                Intent i = new Intent(NewsActivity.this, DetailActivity.class);
                i.putExtra("title", listNews.get(position).getTitle());
                i.putExtra("content", listNews.get(position).getContent());
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

    @Override
    public void onClick(View v) {
        finish();
    }
}
