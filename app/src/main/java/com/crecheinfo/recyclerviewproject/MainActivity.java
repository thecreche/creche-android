package com.crecheinfo.recyclerviewproject;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout mySwipeRefreshLayout;
    RecyclerView recyclerView;
    CoordinatorLayout homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        recyclerView = findViewById(R.id.card_recycler_view);
        homeLayout = findViewById(R.id.homeLayout);
        fetchCreches();

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        fetchCreches();
                    }
                }
        );
    }

    private void displayCreches(List<Creche> crecheList) {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        CrecheListAdapter adapter = new CrecheListAdapter(getApplicationContext(), crecheList);
        recyclerView.setAdapter(adapter);

    }

    private void fetchCreches() {
        final ProgressBar progressBar = findViewById(R.id.progressbar);
        TextView textView = findViewById(R.id.connectivity);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://7amzmvxi9j.execute-api.ap-southeast-1.amazonaws.com/Prod/")//*
                .addConverterFactory(GsonConverterFactory.create())//*
                .build();

        CrecheService service = retrofit.create(CrecheService.class);//CrecheService is API

        // Check for Internet network availability
        // If internet available call the service to fetch data
        // else show network unavailability error on screen

        InternetConnection internetConnection;
        internetConnection = new InternetConnection(getApplicationContext());
        if (internetConnection.isConnectingToInternet() == true) {
            textView.setVisibility(View.GONE);
            Call<List<Creche>> creches = service.getCreches();//Calling Lists of Creches

            if (!mySwipeRefreshLayout.isRefreshing())
                progressBar.setVisibility(View.VISIBLE);

            creches.enqueue(new Callback<List<Creche>>() {//meaning of enqueue is unknown

                @Override
                public void onResponse(Call<List<Creche>> call, Response<List<Creche>> response) {
                    List<Creche> crecheList = response.body();


                    if (mySwipeRefreshLayout.isRefreshing())
                        mySwipeRefreshLayout.setRefreshing(false);
                    else
                        progressBar.setVisibility(View.GONE);

                    displayCreches(crecheList);
                }

                @Override
                public void onFailure(Call<List<Creche>> call, Throwable t) {


                }

            });
        } else {
            if (mySwipeRefreshLayout.isRefreshing()) {
                mySwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(homeLayout, "Unable to refresh data", Snackbar.LENGTH_SHORT).show();
            } else
                textView.setVisibility(View.VISIBLE);
        }
    }
}
