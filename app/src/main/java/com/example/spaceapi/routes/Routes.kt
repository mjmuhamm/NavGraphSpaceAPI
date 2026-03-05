package com.example.spaceapi.routes

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Detail : Routes("detail/{userId}") {
        fun createRoute(userId: String) = "detail/$userId"
    }
}