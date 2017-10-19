package com.tcsr.thecaosieure.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;

import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.models.TransactionObject;

import java.util.ArrayList;

/**
 * Created by lequy on 10/18/2017.
 */

public class ListPaidCardAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TransactionObject> listTrans;

    public ListPaidCardAdapter(Context context, ArrayList<TransactionObject> listTrans) {
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
            convertView = inflater.inflate(R.layout.item_paid_card, parent, false);
        }

        WebView webView = (WebView) convertView.findViewById(R.id.web_view);
        webView.loadDataWithBaseURL(null, listTrans.get(position).getContent(), "text/html", "utf-8", null);

        return convertView;
    }
}
