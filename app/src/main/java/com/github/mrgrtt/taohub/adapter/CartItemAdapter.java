package com.github.mrgrtt.taohub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.activity.DetailActivity;
import com.github.mrgrtt.taohub.model.CartListItem;

import java.math.BigDecimal;
import java.util.List;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<CartListItem> data;
    private Context context;
    private OnDelete onDelete;

    public CartItemAdapter(List<CartListItem> data, Context context, OnDelete delete) {
        this.data = data;
        this.context = context;
        this.onDelete = delete;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, final int position) {
        final CartListItem item = data.get(position);
        Glide.with(context).load(item.getProduct().getThumbnail())
                .centerCrop().into(holder.image);
        holder.count.setText(String.valueOf(item.getCount()));
        String priceS = BigDecimal.valueOf(data.get(position).getPrice())
                .divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        holder.price.setText(priceS);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(data.indexOf(item));
                data.remove(item);
                onDelete.delete(item);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DATA_KEY, item.getProduct());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView image;
        public TextView count;
        public TextView price;
        public Button delete;

        public CartItemViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image);
            count = view.findViewById(R.id.count);
            price = view.findViewById(R.id.price);
            delete = view.findViewById(R.id.delete);
        }
    }

    public void setData(List<CartListItem> data) {
        this.data = data;
    }

    public static interface OnDelete {
        void delete(CartListItem item);
    }
}
