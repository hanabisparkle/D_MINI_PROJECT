package com.example.d_mini_project.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CardsData(
    val name: String,
    val image: String,
    val meaning: String
)

