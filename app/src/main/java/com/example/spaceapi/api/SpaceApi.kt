package com.example.spaceapi.api

import com.example.spaceapi.model.firstPage.SpaceResponse
import retrofit2.http.GET

interface SpaceApi {

    @GET("articles")
    suspend fun getInfo() : SpaceResponse
}

