package com.tcsrmobile.thecaosieure.adapters;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcsrmobile.thecaosieure.R;
import com.tcsrmobile.thecaosieure.models.TransactionObject;

import java.util.ArrayList;

/**
 * Created by lequy on 4/14/2017.
 */

public class ListNotiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TransactionObject> listTrans;

    public ListNotiAdapter(Context context, ArrayList<TransactionObject> listTrans) {
        this.context = context;
        this.listTrans = listTrans;
    }

    @Override
    public int getCount() {
        return listTrans.size();
    }

    @Override
    public Object getItem(int position) {
        return listTrans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_noti, parent, false);
        }

        TextView textContent = (TextView) convertView.findViewById(R.id.text_content);

        Spanned sp = Html.fromHtml(listTrans.get(position).getContent());
        textContent.setText(sp);

        return convertView;
    }
}
