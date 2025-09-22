package com.example.d_mini_project.api

import com.example.d_mini_project.model.CardsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://roxyapi.com/api/v1/data/astro/tarot/single-card-draw?token=b8409149-4d9c-4e2f-8d0d-1f14879294b2
interface ApiService {
    @GET("data/astro/tarot/single-card-draw?token=b8409149-4d9c-4e2f-8d0d-1f14879294b2")
    fun getCardsData(): Call<CardsData>
}