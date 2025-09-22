package com.example.d_mini_project

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.d_mini_project.api.ApiService
import com.example.d_mini_project.model.CardsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.collections.firstOrNull

class MainActivity : AppCompatActivity() {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://roxyapi.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val apiService by lazy{
        retrofit.create(ApiService::class.java)
    }

    private val apiResponseView: TextView by lazy{
        findViewById(R.id.tarot_name_value)
    }

//    private val imageResultView: ImageView by lazy {
//        findViewById(R.id.image_result)
//    }
    private val imageLoader: ImageLoader by lazy {
        GlideLoader(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        getCardsDataResponse()
    }

    private fun getCardsDataResponse() {
        Log.d(MAIN_ACTIVITY, "GETCATIMAGERESPONSE1")
        val call = apiService.getCardsData()

        call.enqueue(
            object: Callback<CardsData> {
                override fun onFailure(call: Call<CardsData>, t: Throwable) {
                    Log.e(MAIN_ACTIVITY, "Failed to get response", t)
                }

                override fun onResponse(call: Call<CardsData>, response: Response<CardsData>) {
                    if (response.isSuccessful){
                        val data: CardsData? = response.body()

                        if (data != null) {
                            apiResponseView.text = data.name
                        } else {
                            Log.w(MAIN_ACTIVITY, "Response successful but body was null.")
                        }

                    } else {
                        Log.e(
                            MAIN_ACTIVITY, "Failed to get response\n" +
                                response.errorBody()?.string().orEmpty()
                        )
                    }
                }

            }
        )

    }
    companion object{
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}

