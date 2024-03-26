package com.example.movie_list_assessment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context mContext;
    private List<MovieModelClass> mData;

    public Adapter(Context mContext, List<MovieModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position){

        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImage())
                .into(holder.image);
//        Glide.with(mContext).load(mData.get(position).getImage()).into(holder.image);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", mData.get(position).getId());
                bundle.putString("overview", mData.get(position).getName());
                Glide.with(mContext)
                        .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImage())
                        .into(holder.image);
                bundle.putString("poster_path",mData.get(position).getImage());




                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {

        return mData.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView name;
        TextView overview;
        ImageView image;


        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            id = itemView.findViewById(R.id.id_txt);
            name = itemView.findViewById(R.id.name_txt);
            image = itemView.findViewById(R.id.imageView);
            relativeLayout = itemView.findViewById(R.id.main_layout);
        }



    }
}
