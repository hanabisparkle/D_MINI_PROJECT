package com.example.d_mini_project

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.d_mini_project.api.ApiService
import com.example.d_mini_project.model.CardsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://roxyapi.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val nameView: TextView by lazy { findViewById(R.id.tarot_name_value) }
    private val meaningView: TextView by lazy { findViewById(R.id.tarot_meaning_value) }
    private val imageResultView: ImageView by lazy { findViewById(R.id.image_result) }
    private val buttonDraw: Button by lazy { findViewById(R.id.button_draw) }

    private val imageLoader: ImageLoader by lazy { GlideLoader(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Button untuk refresh kartu
        buttonDraw.setOnClickListener {
            getCardsDataResponse()
        }

        // Tampilkan kartu pertama saat app dijalankan
        getCardsDataResponse()
    }

    private fun getCardsDataResponse() {
        apiService.getCardsData().enqueue(object : Callback<CardsData> {

            override fun onFailure(call: Call<CardsData>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
            }

            override fun onResponse(call: Call<CardsData>, response: Response<CardsData>) {
                if (response.isSuccessful) {
                    val data: CardsData? = response.body()
                    if (data != null) {
                        nameView.text = data.name
                        meaningView.text = data.meaning

                        if (data.image.isNotEmpty()) {
                            // Load gambar dengan Glide agar proporsional
                            imageLoader.loadImage(data.image, imageResultView)
                            imageResultView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                            imageResultView.adjustViewBounds = true
                        }

                    } else {
                        Log.w(MAIN_ACTIVITY, "Response successful but body was null.")
                    }

                } else {
                    Log.e(
                        MAIN_ACTIVITY,
                        "Failed to get response\n" + response.errorBody()?.string().orEmpty()
                    )
                }
            }

        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}

