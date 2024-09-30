package com.adyen.android.assignment.repositories

sealed class PlanetaryResult {
    data object Success : PlanetaryResult()
    data class ErrorCode(val code: Int) : PlanetaryResult()
    data class ErrorException(val e: Exception) : PlanetaryResult()
}