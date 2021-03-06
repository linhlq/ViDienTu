package com.tcsrmobile.thecaosieure.activities;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tcsrmobile.thecaosieure.MyApplication;
import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.models.PageRequest;
import com.tcsrmobile.thecaosieure.retrofit.IRetrofitAPI;
import com.tcsrmobile.thecaosieure.utils.Constant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by lequy on 4/14/2017.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    private Gson mGson;
    private Retrofit mRetrofit;
    private IRetrofitAPI mRetrofitAPI;

    private ImageView backButton;
    private TextView titleText;
    private TextView contentText;
    private String title;
    private String url;

    private Call<PageRequest> getContentAPI;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mGson = MyApplication.getGson();
        mRetrofit = MyApplication.getRetrofit();
        mRetrofitAPI = mRetrofit.create(IRetrofitAPI.class);

        backButton = (ImageView) findViewById(R.id.back_btn);
        titleText = (TextView) findViewById(R.id.title_text);
        contentText = (TextView) findViewById(R.id.content_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("title");
            url = extras.getString("url");
        }

        titleText.setText(title);
        if (url != null) {
            getContent();
        }

        Constant.increaseHitArea(backButton);
        backButton.setOnClickListener(this);
    }

    private void getContent() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("pagename", url.substring(url.lastIndexOf("=") + 1));

        getContentAPI = mRetrofitAPI.getContentByName(body);
        getContentAPI.enqueue(new Callback<PageRequest>() {
            @Override
            public void onResponse(Call<PageRequest> call, Response<PageRequest> response) {
                int errorCode = response.body().getErrorCode();
                String msg = response.body().getMsg();

                if (errorCode == 1) {
                    Spanned sp = Html.fromHtml(response.body().getData().getFulldescription());
                    contentText.setText(sp);
                } else {
                    Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PageRequest> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
