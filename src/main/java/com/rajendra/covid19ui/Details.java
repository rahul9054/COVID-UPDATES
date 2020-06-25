package com.rajendra.covid19ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Details extends Fragment {
    Map<String, String> map = new HashMap<>();


    String[] stateArray = {"Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttarakhand",
            "Uttar Pradesh",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi",
            "Lakshadweep",
            "Puducherry"};

    AutoCompleteTextView actv;
    TextView textView20;
    TextView textView19;
    TextView textView26;
    TextView textView21;
    TextView textView23;
    List<StateWiseCaseDetails> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View groupRootView = inflater.inflate(R.layout.activity_details, container, false);
        actv = groupRootView.findViewById(R.id.autoCompleteTextView);
        textView20 = groupRootView.findViewById(R.id.textView20);
        textView19 = groupRootView.findViewById(R.id.textView19);
        textView26 = groupRootView.findViewById(R.id.textView26);
        textView21 = groupRootView.findViewById(R.id.textView21);
        textView23 = groupRootView.findViewById(R.id.textView23);

        displayState_info();
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, stateArray);


        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView20.setText(actv.getText());
                textView26.setText(actv.getText());
                textView19.setText(map.get(actv.getText().toString()));
                textView21.setText(map.get(actv.getText().toString() + "1"));
                textView23.setText(map.get(actv.getText().toString() + "2"));


            }
        });

        return groupRootView;
    }

    private void displayState_info() {

        String url = "https://api.covid19india.org/data.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONArray state = obj.getJSONArray("statewise");
                    Log.e("check", state.length() + "  >>>>>>>>>>>>>>>>>>>>>>>>>>");
                    for (int i = 0; i < state.length(); i++) {
                        map.put(state.getJSONObject(i).getString("state"), state.getJSONObject(i).getString("deltaconfirmed"));
                        Log.e("check", state.getJSONObject(i).getString("state"));
                        map.put(state.getJSONObject(i).getString("state") + "1", state.getJSONObject(i).getString("confirmed"));
                        map.put(state.getJSONObject(i).getString("state") + "2", state.getJSONObject(i).getString("recovered"));
                    }


                } catch (JSONException e) {
                    Log.e("check", "error");
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);
    }

}





