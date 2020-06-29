package com.github.mrgrtt.taohub.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.activity.DetailActivity;
import com.github.mrgrtt.taohub.model.Product;
import com.github.mrgrtt.taohub.util.PriceUtil;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> data;
    private Context context;

    public ProductAdapter(List<Product> ps, Context ctx) {
        this.data = ps;
        this.context = ctx;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goods_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        String price = PriceUtil.convert(data.get(position).getPrice());
        holder.price.setText(price);
        Glide.with(context).load(data.get(position).getThumbnail())
                .centerCrop().into(holder.image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DATA_KEY, data.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView price;
        public View view;

        public ProductViewHolder(View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
        }
    }
}
