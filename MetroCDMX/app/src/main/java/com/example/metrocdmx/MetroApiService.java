package com.example.metrocdmx;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MetroApiService {
    @GET("movilidad/METRO/linea")
    Call<List<Linea>> getLineas();
}