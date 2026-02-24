package com.example.spaceapi.viewModel

import com.example.spaceapi.model.firstPage.Result
import com.example.spaceapi.model.secondPage.SecondResponse

sealed class SpaceState {
    object Loading: SpaceState()
    data class Success(val data: List<Result>): SpaceState()
    data class Error(val message: String): SpaceState()
}

sealed class SecondState {
    object Loading: SecondState()
    data class Success(val data: SecondResponse): SecondState()
    data class Error(val message: String): SecondState()
}