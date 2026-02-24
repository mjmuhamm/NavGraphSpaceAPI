package com.example.spaceapi.model.firstPage

data class SpaceResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)