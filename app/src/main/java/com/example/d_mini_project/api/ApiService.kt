package com.example.d_mini_project.api

import com.example.d_mini_project.model.CardsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://roxyapi.com/api/v1/data/astro/tarot/single-card-draw?token=0cd710c0-bfb8-452f-b2f3-dc323225e049
interface ApiService {
    @GET("data/astro/tarot/single-card-draw?token=0cd710c0-bfb8-452f-b2f3-dc323225e049")
    fun getCardsData(): Call<CardsData>
}