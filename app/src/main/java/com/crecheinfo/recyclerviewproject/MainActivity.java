package com.crecheinfo.recyclerviewproject;

import android.app.LoaderManager;
import android.content.ClipData;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.UserDictionary.Words.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

//    private final String android_version_name[] = {
//            "Donut",
//            "Eclair",
//            "Froyo",
//            "Gingerbread",
//            "Honeycomb",
//            "Ice Cream Sandwich",
//            "Jelly Bean",
//            "KitKat",
//            "Lollipop",
//            "Marshmallow"
//    };
//
//    private final String android_image_url[] = {
//             "http://api.learn2crack.com/android/images/donut.png",
//            "http://api.learn2crack.com/android/images/eclair.png",
//            "http://api.learn2crack.com/android/images/froyo.png",
//            "http://api.learn2crack.com/android/images/ginger.png",
//            "http://api.learn2crack.com/android/images/honey.png",
//            "http://api.learn2crack.com/android/images/icecream.png",
//            "http://api.learn2crack.com/android/images/jellybean.png",
//            "http://api.learn2crack.com/android/images/kitkat.png",
//            "http://api.learn2crack.com/android/images/lollipop.png",
//            "http://api.learn2crack.com/android/images/marshmallow.png"
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar progressBar = findViewById(R.id.progressbar);

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

            Call<List<Creche>> creches = service.getCreches();//Calling Lists of Creches
            progressBar.setVisibility(View.VISIBLE);

            creches.enqueue(new Callback<List<Creche>>() {//meaning of enqueue is unknown

                @Override
                public void onResponse(Call<List<Creche>> call, Response<List<Creche>> response) {
                    List<Creche> crecheList = response.body();
                    progressBar.setVisibility(View.GONE);
                    displayCreches(crecheList);
                }

                @Override
                public void onFailure(Call<List<Creche>> call, Throwable t) {


                }

            });
        }
        else {
            TextView textView=findViewById(R.id.connectivity);


        }


        //initViews();
    }



    private void displayCreches(List<Creche> crecheList) {

        RecyclerView recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        CrecheListAdapter adapter = new CrecheListAdapter(getApplicationContext(), crecheList);
        recyclerView.setAdapter(adapter);

    }

    private void initViews() {

//        RecyclerView recyclerView = findViewById(R.id.card_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        ArrayList androidVersion = prepareData();
//        CrecheListAdapter adapter = new CrecheListAdapter(getApplicationContext(), androidVersion);
//        recyclerView.setAdapter(adapter);

    }

//    private ArrayList prepareData() {
//        ArrayList android_version = new ArrayList();
//        for (int i = 0; i < android_version_name.length; i++) {
//            AndroidVersion androidVersion = new AndroidVersion();
//            androidVersion.setAndroid_version_name(android_version_name[i]);
//            androidVersion.setAndroid_image_url(android_image_url[i]);
//            android_version.add(androidVersion);
//        }
//        return android_version;
//    }

}
