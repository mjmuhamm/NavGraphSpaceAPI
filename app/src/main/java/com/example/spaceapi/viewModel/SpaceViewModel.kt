package com.example.spaceapi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceapi.model.secondPage.SecondResponse
import com.example.spaceapi.repository.SpaceRepository
import kotlinx.coroutines.launch

class SpaceViewModel(private val repository: SpaceRepository = SpaceRepository()) : ViewModel() {

    private val _spaceState = MutableLiveData<SpaceState>(SpaceState.Loading)
    val spaceState = _spaceState


init {
    getInfo()
}

    fun getInfo() {
        viewModelScope.launch {
            _spaceState.value = SpaceState.Loading
            val result = repository.getInfo()
            _spaceState.value = if (result.isSuccess) {
                val info = result.getOrNull() ?: emptyList()
                SpaceState.Success(info)
            } else {
                SpaceState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }
    }


}