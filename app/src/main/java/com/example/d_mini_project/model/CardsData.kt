package com.example.d_mini_project.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class CardsData(
    val name: String,
    val image: String,
    val meaning: String, // nama variable harus sama dengan yang di json

    // koding tambahan
//    val isReversed: Boolean,
//    val reversed_meaning: String
)

