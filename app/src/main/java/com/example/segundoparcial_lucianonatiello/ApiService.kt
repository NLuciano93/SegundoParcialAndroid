package com.example.segundoparcial_lucianonatiello

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {


    @GET
    suspend fun getJokeRandom(@Url url: String): Response<ChuckJoke>

    @GET
    suspend fun getJokeRandomCategory(@Url url: String): Response<ChuckJoke>

    @GET
    suspend fun getCategories(@Url url: String): Response<ArrayList<String>>


}