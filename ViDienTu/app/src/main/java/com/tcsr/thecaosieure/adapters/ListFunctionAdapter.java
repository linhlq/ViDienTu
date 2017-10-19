package com.tcsr.thecaosieure.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcsr.thecaosieure.R;
import com.tcsr.thecaosieure.models.MenuObject;

import java.util.ArrayList;

/**
 * Created by Linh Lee on 4/9/2017.
 */
public class ListFunctionAdapter extends RecyclerView.Adapter<ListFunctionAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<MenuObject> listFunction;
    private int layout;

    private PositionClickListener listener;
    private int selectedPos = 0;

    public ListFunctionAdapter(Context context, ArrayList<MenuObject> listFunction, int layout, PositionClickListener listener) {
        this.context = context;
        this.listFunction = listFunction;
        this.layout = layout;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(layout, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.functionImg.setImageResource(listFunction.get(position).getImgRes());
        holder.functionName.setText(listFunction.get(position).getName());

        holder.itemView.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return listFunction.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface PositionClickListener {
        void itemClicked(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView functionImg;
        private TextView functionName;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            functionImg = (ImageView) itemView.findViewById(R.id.function_img);
            functionName = (TextView) itemView.findViewById(R.id.function_name);

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
