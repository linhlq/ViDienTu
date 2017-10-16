package com.linhlee.vidientu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.models.CardObject;
import com.linhlee.vidientu.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lequy on 4/27/2017.
 */

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<CardObject> listCard;

    private PositionClickListener listener;
    private int selectedPos = 0;

    public ListCardAdapter(Context context, ArrayList<CardObject> listCard, PositionClickListener listener) {
        this.context = context;
        this.listCard = listCard;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_card, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(context).load(Constant.IMAGE_CARD_URL + listCard.get(position).getImage()).into(holder.cardImg);
        holder.textChietKhau.setText("Chiết khấu " + String.format("%.1f", 100 - listCard.get(position).getDisDPT()) + "%");

        holder.itemView.setSelected(selectedPos == position);

        if (selectedPos == position) {
            holder.border.setVisibility(View.VISIBLE);
        } else {
            holder.border.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface PositionClickListener {
        void itemClicked(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView cardImg;
        public TextView textChietKhau;
        public View border;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardImg = (ImageView) itemView.findViewById(R.id.card_img);
            textChietKhau = (TextView) itemView.findViewById(R.id.text_chiet_khau);
            border = itemView.findViewById(R.id.border);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.itemClicked(getLayoutPosition());
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }
    }
}
