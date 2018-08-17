package com.crecheinfo.recyclerviewproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CrecheService {

    @GET("creches")
    Call<List<Creche>> getCreches();

    @GET("creches/{id}")
    Call<Creche> getCrecheDetails(@Path("id") int crecheId);
}
