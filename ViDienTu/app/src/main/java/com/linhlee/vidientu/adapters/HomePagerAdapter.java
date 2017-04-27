package com.linhlee.vidientu.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linhlee.vidientu.R;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class HomePagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Integer> listImgRes;
    private LayoutInflater inflater;

    public HomePagerAdapter(Context context, ArrayList<Integer> listImgRes) {
        this.context = context;
        this.listImgRes = listImgRes;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.item_home_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
        imageView.setImageResource(listImgRes.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return listImgRes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}