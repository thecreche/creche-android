package com.crecheinfo.recyclerviewproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CrecheService {

    @GET("creches")
    Call<List<Creche>> getCreches();

}
