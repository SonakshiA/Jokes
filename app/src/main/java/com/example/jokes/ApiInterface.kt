package com.example.jokes

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("random_ten")
    fun getData() : Call<ResponseDataClass>

}