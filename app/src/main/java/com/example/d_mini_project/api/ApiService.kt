package com.example.d_mini_project.api

import com.example.d_mini_project.model.CardsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/astro/tarot/single-card-draw?token=143eb6a7-3074-436f-a5f6-e22b1f930208")
    fun getCardsData(): Call<CardsData>
}