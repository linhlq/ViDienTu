package com.linhlee.vidientu.fragments.mainfragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.adapters.HomePagerAdapter;
import com.linhlee.vidientu.adapters.ListFunctionAdapter;
import com.linhlee.vidientu.adapters.ListNewsAdapter;
import com.linhlee.vidientu.fragments.BaseFragment;
import com.linhlee.vidientu.models.MenuObject;
import com.linhlee.vidientu.models.NewsObject;
import com.linhlee.vidientu.utils.Constant;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class HomeFragment extends BaseFragment {
    private ViewPager pager;
    private HomePagerAdapter pagerAdapter;
    private ArrayList<Integer> listImgRes;
    private ImageView[] mDots;
    private LinearLayout mLinearLayout;
    private RecyclerView listFunctionView;
    private ListFunctionAdapter listFunctionAdapter;
    private ArrayList<MenuObject> listFunction;
    private RecyclerView listNewsView;
    private ListNewsAdapter listNewsAdapter;
    private ArrayList<NewsObject> listNews;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.viewPagerCountDots);
        listFunctionView = (RecyclerView) rootView.findViewById(R.id.list_function);
        listNewsView = (RecyclerView) rootView.findViewById(R.id.list_news);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //Create slide image
        listImgRes = new ArrayList<>();
        listImgRes.add(R.mipmap.bg_sample);
        listImgRes.add(R.mipmap.bg_sample);
        listImgRes.add(R.mipmap.bg_sample);
        listImgRes.add(R.mipmap.bg_sample);

        pagerAdapter = new HomePagerAdapter(getActivity(), listImgRes);
        pager.setAdapter(pagerAdapter);

        drawPageSelectionIndicators(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                drawPageSelectionIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Create list function
        listFunction = new ArrayList<>();
        listFunction.add(new MenuObject(R.mipmap.ic_about_us, getActivity().getResources().getString(R.string.gioi_thieu)));
        listFunction.add(new MenuObject(R.mipmap.ic_deposit, getActivity().getResources().getString(R.string.nap_tien)));
        listFunction.add(new MenuObject(R.mipmap.ic_withdraw, getActivity().getResources().getString(R.string.rut_tien)));
        listFunction.add(new MenuObject(R.mipmap.ic_transfer_money, getActivity().getResources().getString(R.string.chuyen_tien)));
        listFunction.add(new MenuObject(R.mipmap.ic_viettel, getActivity().getResources().getString(R.string.viettel)));

        listFunctionAdapter = new ListFunctionAdapter(getActivity(), listFunction, new ListFunctionAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {

            }
        });
        listFunctionAdapter.setHasStableIds(true);

        listFunctionView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listFunctionView.setAdapter(listFunctionAdapter);

        //Create list news
        listNews = new ArrayList<>();
        listNews.add(new NewsObject(R.mipmap.bg_sample, "Tặng ngay 100.000đ khi liên kết tài khoản Viettel", "Nhận ngay 100.000đ để nạp tiền điện thoại khi bạn làm cái quần què gì đấy"));
        listNews.add(new NewsObject(R.mipmap.bg_sample, "Tặng ngay 100.000đ khi liên kết tài khoản Viettel", "Nhận ngay 100.000đ để nạp tiền điện thoại khi bạn làm cái quần què gì đấy"));
        listNews.add(new NewsObject(R.mipmap.bg_sample, "Tặng ngay 100.000đ khi liên kết tài khoản Viettel", "Nhận ngay 100.000đ để nạp tiền điện thoại khi bạn làm cái quần què gì đấy"));
        listNews.add(new NewsObject(R.mipmap.bg_sample, "Tặng ngay 100.000đ khi liên kết tài khoản Viettel", "Nhận ngay 100.000đ để nạp tiền điện thoại khi bạn làm cái quần què gì đấy"));
        listNews.add(new NewsObject(R.mipmap.bg_sample, "Tặng ngay 100.000đ khi liên kết tài khoản Viettel", "Nhận ngay 100.000đ để nạp tiền điện thoại khi bạn làm cái quần què gì đấy"));

        listNewsAdapter = new ListNewsAdapter(getActivity(), listNews, new ListNewsAdapter.PositionClickListener() {
            @Override
            public void itemClicked(int position) {

            }
        });
        listNewsAdapter.setHasStableIds(true);

        listNewsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listNewsView.setAdapter(listNewsAdapter);
    }

    private void drawPageSelectionIndicators(int mPosition){
        int margin = Constant.convertDpIntoPixels(2, getActivity());

        if(mLinearLayout!=null) {
            mLinearLayout.removeAllViews();
        }

        mDots = new ImageView[listImgRes.size()];

        //set image with orange circle if mDots[i] == mPosition
        for (int i = 0; i < listImgRes.size(); i++) {
            mDots[i] = new ImageView(getActivity());
            if(i == mPosition)
                mDots[i].setImageDrawable(getResources().getDrawable(R.mipmap.ic_circle_selected));
            else
                mDots[i].setImageDrawable(getResources().getDrawable(R.mipmap.ic_circle_unselected));

            //set layout for circle indicators
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            mDots[i].setPadding(margin, 0, margin, 0);
            mLinearLayout.addView(mDots[i], params);
        }
    }
}
