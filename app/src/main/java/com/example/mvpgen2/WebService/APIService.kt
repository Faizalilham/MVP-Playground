package com.example.mvpgen2.WebService

import android.content.Context
import android.content.Context.MODE_PRIVATE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIService {
    companion object {
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            } else {
                retrofit!!
            }
        }

        fun APIEndPoint(): APIClient = getClient().create(APIClient::class.java)
    }
}

class Constant {
    companion object {
        const val BASE_URL = "http://apibarang.herokuapp.com/"

        fun getToken(context: Context): String {
            val preference = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            val token = preference.getString("TOKEN", "UNDEFINED")
            return token!!
        }

        fun setToken(context: Context, value: String?) {
            val preference = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            preference.edit().apply {
                putString("TOKEN", value)
                apply()
            }
        }

        fun delToken(context: Context) {
            val preference = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            preference.edit().clear().apply()
        }
    }
}