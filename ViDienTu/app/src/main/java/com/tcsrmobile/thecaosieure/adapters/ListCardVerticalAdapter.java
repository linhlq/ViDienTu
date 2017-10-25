package com.tcsrmobile.thecaosieure.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.models.CardObject;
import com.tcsrmobile.thecaosieure.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lequy on 10/18/2017.
 */

public class ListCardVerticalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CardObject> listCard;

    public ListCardVerticalAdapter(Context context, ArrayList<CardObject> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    @Override
    public int getCount() {
        return listCard.size();
    }

    @Override
    public Object getItem(int position) {
        return listCard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_card_vertical, parent, false);
        }

        ImageView cardImg = (ImageView) convertView.findViewById(R.id.card_img);
        TextView cardName = (TextView) convertView.findViewById(R.id.card_name);

        Picasso.with(context).load(Constant.IMAGE_CARD_URL + listCard.get(position).getImage()).into(cardImg);
        cardName.setText(listCard.get(position).getBankName());

        return convertView;
    }
}
