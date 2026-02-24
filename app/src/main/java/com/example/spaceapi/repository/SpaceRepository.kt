package com.example.spaceapi.repository

import com.example.spaceapi.api.SpaceApi
import com.example.spaceapi.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpaceRepository(private val api: SpaceApi = RetrofitClient.api) {

    suspend fun getInfo(): Result<List<com.example.spaceapi.model.firstPage.Result>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getInfo()
            val info = response.results
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}