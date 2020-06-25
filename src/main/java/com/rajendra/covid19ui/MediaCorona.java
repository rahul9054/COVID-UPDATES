package com.rajendra.covid19ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MediaCorona extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3 ;

    private ArrayList<String> list2;
    private ArrayList<String> list1;
    CelebsAdapter adapter;
    GovermentVideo adapter2 ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View groupRootView = inflater.inflate(R.layout.activity_youtube, container, false);

        recyclerView = groupRootView.findViewById(R.id.categories_recycler);
        recyclerView2 = groupRootView.findViewById(R.id.most_viewed_recycler);
        list2 = new ArrayList<>();
        list2.add("xwWeEZRlLRk") ;
        list2.add("c-SPuK7Xkqc") ;
        list2.add("CVHzup-jPa4") ;

        adapter = new CelebsAdapter(getContext(), list2);
        adapter2 = new GovermentVideo(getContext(), list2);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);

       // displayVideo();

          return groupRootView ;
    }

    private void displayVideo() {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UC07-dOwgza1IguKA86jqxNA&maxResults=12&key=AIzaSyDr0Edf6q0uWWQWaMrFNmDbL1XA-68q_4o";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray jsonArray = obj.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
                        JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                        String title = jsonObjectSnippet.getString("title") ;
                        JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        String videoId = jsonVideoId.getString("videoId");
                        VideoDetails videoDetails = new VideoDetails();
                        videoDetails.setVideoId(videoId);
                        videoDetails.setTitle(title);
                        videoDetails.setUrl(jsonObjectDefault.getString("url"));
                        Toast.makeText(getContext(), jsonObjectDefault.getString("url") + "hii", Toast.LENGTH_LONG).show();

//                        list.add(videoDetails);


                    }

                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), volleyError.getMessage() + "hii", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }


}
