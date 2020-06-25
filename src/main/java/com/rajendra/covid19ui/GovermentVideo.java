package com.rajendra.covid19ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class GovermentVideo extends RecyclerView.Adapter<GovermentVideo.Details> {
    public ArrayList<String> list;
    Context context;

    public GovermentVideo(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public GovermentVideo.Details onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new Details(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GovermentVideo.Details holder, int position) {
        //Picasso.get().load(list.get(position).getUrl()).into(holder.imageView);
        holder.youTubeThumbnailView.initialize("rahul", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(list.get(position));
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        holder.youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , VideoActivity.class) ;
                intent.putExtra("id" , list.get(position)) ;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Details extends RecyclerView.ViewHolder {

        YouTubeThumbnailView youTubeThumbnailView;


        public Details(@NonNull View itemView) {
            super(itemView);
            youTubeThumbnailView = itemView.findViewById(R.id.mv_image);
        }
    }
}
