package com.example.d_mini_project.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true) // MODEL UNTUK MENAMPUNG DATA DARI JSON
data class CardsData(
    // nama variable harus sama dengan yang di json
    val name: String,
    val image: String,
    val meaning: String //,

    // HASIL LIVE CODING (mengakses 2 data tambahan dari JSON)
//    @field:Json(name="is_reversed") val isReversed: Boolean,
//    @field:Json(name="reversed_meaning") val revMeaning: String
    // (cara memakai nama variable yang beda dari yang di json)
)

