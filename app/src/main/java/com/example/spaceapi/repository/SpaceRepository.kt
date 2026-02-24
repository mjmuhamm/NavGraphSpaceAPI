package com.example.spaceapi.repository

import com.example.spaceapi.api.SecondPageApi
import com.example.spaceapi.api.SpaceApi
import com.example.spaceapi.remote.RetrofitClient
import com.example.spaceapi.remote.SecondRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpaceRepository(private var api: SpaceApi = RetrofitClient.api, private var api2: SecondPageApi = SecondRetrofitClient.api) {

    suspend fun getInfo(): Result<List<com.example.spaceapi.model.firstPage.Result>> = withContext(Dispatchers.IO) {

        try {
            val response = api.getInfo()
            val info = response.results
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSecondPageInfo(id: String): Result<com.example.spaceapi.model.secondPage.SecondResponse> = withContext(Dispatchers.IO) {
        try {
            val info = api2.getInfo(id)
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}