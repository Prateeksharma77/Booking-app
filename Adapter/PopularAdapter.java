package com.example.booking_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booking_app.Activity.DetailActivity;
import com.example.booking_app.Domain.ItemDomain;
import com.example.booking_app.databinding.ViewholderPopularBinding;
import com.example.booking_app.databinding.ViewholderRecommendedBinding;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<ItemDomain> items;
    Context context;
    ViewholderPopularBinding binding;
    public PopularAdapter(ArrayList<ItemDomain> items){
        this.items=items;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ViewholderPopularBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context=parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        binding.textView9.setText(items.get(position).getTitle());
        binding.textView12.setText("Rs" + items.get(position).getPrice());
        binding.textView11.setText(items.get(position).getAddress());
        binding.textView10.setText("" + items.get(position).getScore());

        Glide.with(context)
                .load(items.get(position).getPic())
                .into(binding.pic);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder( ViewholderPopularBinding binding) {
            super(binding.getRoot());
        }
    }
}
