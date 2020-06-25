package com.rajendra.covid19ui;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoronaFragment extends Fragment {


    TextView confirmedCases;
    TextView active;

    TextView recovered;
    TextView deceased;
    FusedLocationProviderClient client;
    LocationRequest mLocationRequest;
    LocationCallback locationCallback;
    GeoFire geoFire;
    GeoQuery geoQuery;
    int total = 0;
    TextView textView;
    DatabaseReference database;
    Retrofit retrofit;
    TextView textViewDistrict;
    Location location;
    String state;
    String city;
    TextView my_city_name;
    ImageView menu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View groupRootView = inflater.inflate(R.layout.corona_fragment, container, false);

        confirmedCases = groupRootView.findViewById(R.id.textView5);
        active = groupRootView.findViewById(R.id.textView8);
        recovered = groupRootView.findViewById(R.id.textView6);
        deceased = groupRootView.findViewById(R.id.textView7);
        textView = groupRootView.findViewById(R.id.textView_my_location_2);
        textViewDistrict = groupRootView.findViewById(R.id.textView_my_city_2);
        client = LocationServices.getFusedLocationProviderClient(getContext());
        database = FirebaseDatabase.getInstance().getReference("MyLocation");
        my_city_name = groupRootView.findViewById(R.id.textView_my_city_1);
        menu = groupRootView.findViewById(R.id.imageView);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);


        Call<Example> call = service.covidUpdate();

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.e("check", response.body() + "  >>>>>>>>>>>>>>>>>>>>>>>>");
                Log.e("check1", response.toString() + " >>>>>>>>>>>>>>>>>>>>>>>>");
                confirmedCases.setText(response.body().getCasesTimeSeries().get(response.body().getCasesTimeSeries().size() - 1).getTotalconfirmed());
                active.setText(response.body().getCasesTimeSeries().get(response.body().getCasesTimeSeries().size() - 1).getDailyconfirmed());
                recovered.setText(response.body().getCasesTimeSeries().get(response.body().getCasesTimeSeries().size() - 1).getTotalrecovered());
                deceased.setText(response.body().getCasesTimeSeries().get(response.body().getCasesTimeSeries().size() - 1).getTotaldeceased());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
            }
        });


        onConnected();


        return groupRootView;
    }

    public void onConnected() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                location = locationResult.getLastLocation();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> addressList = null;
                    Address address = null;
                    try {
                        addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        address = addressList.get(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (address != null) {
                        state = address.getAdminArea();
                        city = address.getLocality();
                        Log.e("state_city", state + " " + city);
                    }
                    displayState_info(state, city);
                    // setLocation(location);

                    client.removeLocationUpdates(locationCallback);
                }

            }
        };
        client.requestLocationUpdates(mLocationRequest, locationCallback, null);

    }

    private void setLocation(Location location) {


        geoFire = new GeoFire(database);
        geoFire.setLocation("Rahul", new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {

//                 displayLocation(location);
            }


        });


    }

    private void displayLocation(Location location) {

        geoQuery = geoFire.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), 10.0);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {

                Log.v("Corona Virus", key + " " + location.latitude + "," + location.longitude + "///////////////////////////*************************************");
                total = total + 1;
                textView.setText(String.valueOf(total));

            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        client.removeLocationUpdates(locationCallback);
//        geoQuery.removeAllListeners();
    }

    private void displayState_info(String state_name, String city) {
        String url = "https://api.covid19india.org/state_district_wise.json";



        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONObject state = obj.getJSONObject(state_name);
                    JSONObject DistrictData = state.getJSONObject("districtData");
                    JSONObject district = DistrictData.getJSONObject(city);
                    textViewDistrict.setText(district.getString("confirmed"));
                    my_city_name.setText(city);


                } catch (JSONException e) {
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
