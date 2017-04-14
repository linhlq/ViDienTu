package com.linhlee.vidientu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linhlee.vidientu.R;
import com.linhlee.vidientu.models.NewsObject;

import java.util.ArrayList;

/**
 * Created by lequy on 4/14/2017.
 */

public class ListNewsAllAdapter extends RecyclerView.Adapter<ListNewsAllAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<NewsObject> listNews;

    private PositionClickListener listener;
    private int selectedPos = 0;

    public ListNewsAllAdapter(Context context, ArrayList<NewsObject> listNews, PositionClickListener listener) {
        this.context = context;
        this.listNews = listNews;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_news_all, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        /*holder.newsImg.setImageResource(listNews.get(position).getImgRes());
        holder.newsTitle.setText(listNews.get(position).getTitle());
        holder.newsContent.setText(listNews.get(position).getContent());*/

        holder.itemView.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface PositionClickListener {
        void itemClicked(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView newsImg;
        private TextView newsTitle;
        private TextView newsContent;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            newsImg = (ImageView) itemView.findViewById(R.id.news_img);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            newsContent = (TextView) itemView.findViewById(R.id.news_content);

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
