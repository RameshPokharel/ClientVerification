package com.rental.clentverification

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface EcommerceApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("auth/")//http://ecommerce.3callistos.com/api/v1/login
    fun loginUser(
        @Query("data") name: String
    ): Call<ResponseBody>

}