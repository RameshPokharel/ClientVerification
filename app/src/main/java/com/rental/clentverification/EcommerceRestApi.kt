package com.rental.clentverification

import android.content.Context
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


public class EcommerceRestApi(ctx: Context) {
    private val TAG = "CoSysRestAPI"
companion object {
    var mBuilder: Retrofit? = null
    fun getRetrofitClient(): Retrofit? {
        if (mBuilder == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS).build()
            mBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://192.168.1.100/").build()
        }
        return mBuilder
    }
}
}