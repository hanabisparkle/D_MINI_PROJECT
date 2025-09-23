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
    // INSTANSIASI RETROFIT
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://roxyapi.com/api/v1/")
            // moshi sebagai converter factory
            // (sebagai parser JSON)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    // INSTANSIASI APISERVICE
    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // REFERENSI VIEW (dari activity_main.xml) YANG AKAN DIGUNAKAN UNTUK MENAMPILKAN DATA
    private val nameView: TextView by lazy { findViewById(R.id.tarot_name_value) }
    private val meaningView: TextView by lazy { findViewById(R.id.tarot_meaning_value) }
    private val imageResultView: ImageView by lazy { findViewById(R.id.image_result) }
    private val buttonDraw: Button by lazy { findViewById(R.id.button_draw) }

    // INSTANSIASI IMAGE LOADER
    private val imageLoader: ImageLoader by lazy { GlideLoader(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Button untuk refresh kartu
        buttonDraw.setOnClickListener {
            getCardsDataResponse()
        }

        // Untuk menampilkan kartu
        getCardsDataResponse()
    }

    private fun getCardsDataResponse() {
        // apiService.getCardsData() ini mengembalikan object jenis Call
        // Call ini adalah cara retrofit membuat request ke server
        // Parameternya adalah object Callback yang digunakan untuk menangani responsenya
        // (get response or fail to get response, response success or fail)
        apiService.getCardsData().enqueue(object : Callback<CardsData> {

            override fun onFailure(call: Call<CardsData>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
            }

            override fun onResponse(call: Call<CardsData>, response: Response<CardsData>) {
                if (response.isSuccessful) {
                    // Jika response berhasil diterima + response berhasil + data dalam response tidak null
                    // maka data akan ditampilkan
                    val data: CardsData? = response.body()
                    if (data != null) {
                        // log untuk debugging data dari jsonnya
                        Log.d(MAIN_ACTIVITY, data.name)
                        Log.d(MAIN_ACTIVITY, data.meaning)
                        //Log.d(MAIN_ACTIVITY, data.isReversed.toString())
                        //Log.d(MAIN_ACTIVITY, data.revMeaning)
                        Log.d(MAIN_ACTIVITY, data.image)

                        nameView.text = data.name // memasukkan data ke dalam view yang sudah direferensi tadi
                        meaningView.text = data.meaning

                        // menggunakan imageLoader untuk loading image
                        if (data.image.isNotEmpty()) {
                            // Load gambar dengan Glide agar proporsional
                            imageLoader.loadImage(data.image, imageResultView)
                            imageResultView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                            imageResultView.adjustViewBounds = true
                        }

                        // HASIL LIVE CODING (membuat gambarnya terbalik dan menampilkan revMeaning kalau isReversed = true)
//                        nameView.text = data.name
//
//                        if (data.image.isNotEmpty()) {
//                            imageLoader.loadImage(data.image, imageResultView)
//                        }
//
//                        if (data.isReversed) {
//
//                            meaningView.text = data.revMeaning
//
//                            flipImage(imageResultView)
//
//                        } else {
//                            meaningView.text = data.meaning
//                        }
//
//                        if (data.image.isNotEmpty()) {
//                            imageLoader.loadImage(data.image, imageResultView)
//                        }


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

    // HASIL LIVE CODING
    // (function untuk flip image)

//    fun flipImage(imageView: ImageView) {
//        imageView.scaleY = -1f
//    }
    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}

