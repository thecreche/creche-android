package com.crecheinfo.recyclerviewproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        final FrameLayout progressBar = findViewById(R.id.progressbar);


        Intent intent = getIntent();
        String crecheId = intent.getStringExtra(Constants.CRECHE_ID);
        int id = Integer.parseInt(crecheId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://7amzmvxi9j.execute-api.ap-southeast-1.amazonaws.com/Prod/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrecheService service = retrofit.create(CrecheService.class);

        Call<Creche> creche = service.getCrecheDetails(id);
        progressBar.setVisibility(View.VISIBLE);

        creche.enqueue(new Callback<Creche>() {

            @Override
            public void onResponse(Call<Creche> call, Response<Creche> response) {
                Creche creche = response.body();
                progressBar.setVisibility(View.GONE);
                displayCreche(creche);
            }

            @Override
            public void onFailure(Call<Creche> call, Throwable t) {


            }

        });
    }

    private void displayCreche(Creche creche) {

        ImageView image = findViewById(R.id.image_view_detail);
        TextView name = findViewById(R.id.text_view_detail);
        TextView information = findViewById(R.id.text_view_detail_information);

        Picasso.get().setLoggingEnabled(true);

        Picasso.get().load(creche.getImageUrl())
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.ic_launcher_background)
                .into(image);

        name.setText(creche.getName());
        information.setText(creche.getDescription());


    }
}
