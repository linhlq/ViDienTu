package com.tcsr.thecaosieure.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.models.BannerObject;
import com.tcsr.thecaosieure.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class HomePagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BannerObject> listImg;
    private LayoutInflater inflater;

    public HomePagerAdapter(Context context, ArrayList<BannerObject> listImg) {
        this.context = context;
        this.listImg = listImg;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.item_home_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
        Picasso.with(context).load(Constant.IMAGE_BANNER_URL + listImg.get(position).getFilename()).into(imageView);
        //imageView.setImageResource(listImg.get(position));

        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return listImg.size();
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
