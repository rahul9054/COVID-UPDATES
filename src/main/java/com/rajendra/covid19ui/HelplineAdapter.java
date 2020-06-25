package com.rajendra.covid19ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.Help> {
    public String[] list;
    Context context;


    public HelplineAdapter(String[] list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_helpline_item, parent, false);

        return new Help(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Help holder, int position) {
        holder.state.setText(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class Help extends RecyclerView.ViewHolder {
        TextView state;

        public Help(@NonNull View itemView) {

            super(itemView);

            state = itemView.findViewById(R.id.state) ;


        }
    }
}
