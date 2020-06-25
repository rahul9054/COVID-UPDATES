package com.rajendra.covid19ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

public class CelebsAdapter extends RecyclerView.Adapter<CelebsAdapter.Details> {

    public ArrayList<String> list;
    Context context ;

    public CelebsAdapter(Context context, ArrayList<String> list) {
        this.context = context ;
        this.list = list;
    }

    @NonNull
    @Override
    public Details onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new Details(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Details holder, final int position) {
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

        ImageView imageView ;

        YouTubeThumbnailView youTubeThumbnailView ;

        public Details(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagecelebs) ;
            youTubeThumbnailView = itemView.findViewById(R.id.mv_image) ;
        }
    }
}



