package com.linhlee.vidientu.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.TabsPagerAdapter;
import com.linhlee.vidientu.fragments.personalfragments.AvatarFragment;
import com.linhlee.vidientu.fragments.personalfragments.FileFragment;
import com.linhlee.vidientu.fragments.personalfragments.PasswordFragment;
import com.linhlee.vidientu.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backButton;
    private TabLayout tabs;
    private ViewPager pager;
    private TabsPagerAdapter adapter;
    private ArrayList<Fragment> listFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        backButton = (ImageView) findViewById(R.id.back_btn);
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listFragment = new ArrayList<>();
        listFragment.add(FileFragment.newInstance());
        listFragment.add(PasswordFragment.newInstance());
        listFragment.add(AvatarFragment.newInstance());

        adapter = new TabsPagerAdapter(getSupportFragmentManager(), listFragment);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        tab1.setCustomView(createTabView(getResources().getString(R.string.ho_so)));
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        tab2.setCustomView(createTabView(getResources().getString(R.string.mat_khau)));
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        tab3.setCustomView(createTabView(getResources().getString(R.string.anh_dai_dien)));

        Constant.increaseHitArea(backButton);

        backButton.setOnClickListener(this);
    }

    private View createTabView(String title) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.tabs_personal_layout, null);

        TextView tabsTitle = (TextView) itemView.findViewById(R.id.tab_title);
        tabsTitle.setText(title);
        tabsTitle.setTextColor(getResources().getColorStateList(R.color.text_tab_indicator));

        return itemView;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
