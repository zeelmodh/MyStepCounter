package com.example.mystepcounter.Api

import com.example.mystepcounter.dataClasses.ModelPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiInterface {

//    @GET("/posts")
//    fun getPost(@Body blank: Map<String, String>): Call<ModelPost>

    @GET("/posts")
    suspend fun getPost(): Call<ModelPost>
}