package com.github.mrgrtt.taohub.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.github.mrgrtt.taohub.model.HistoryListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<HistoryListItem> data;
    private Context context;

    public HistoryAdapter(List<HistoryListItem> ps, Context ctx) {
        this.data = ps;
        this.context = ctx;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        final HistoryListItem item = data.get(position);
        holder.name.setText(item.getProduct().getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        holder.time.setText(dateFormat.format(new Date(item.getTime())));
        Glide.with(context).load(item.getProduct().getThumbnail())
                .centerCrop().into(holder.image);

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

    public void setData(List<HistoryListItem> data) {
        this.data = data;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView time;
        public View view;

        public HistoryViewHolder(View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            time = view.findViewById(R.id.time);
        }
    }
}
