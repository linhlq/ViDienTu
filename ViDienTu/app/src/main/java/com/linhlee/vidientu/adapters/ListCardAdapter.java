package com.linhlee.vidientu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;

import java.util.ArrayList;

/**
 * Created by lequy on 4/27/2017.
 */

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Integer> listCardImg;

    private PositionClickListener listener;
    private int selectedPos = -1;

    public ListCardAdapter(Context context, ArrayList<Integer> listCardImg, PositionClickListener listener) {
        this.context = context;
        this.listCardImg = listCardImg;
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
        holder.cardImg.setImageResource(listCardImg.get(position));

        holder.itemView.setSelected(selectedPos == position);

        if (selectedPos == position) {
            holder.border.setVisibility(View.VISIBLE);
        } else {
            holder.border.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listCardImg.size();
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
        public View border;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardImg = (ImageView) itemView.findViewById(R.id.card_img);
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
