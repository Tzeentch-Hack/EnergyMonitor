package com.tzeentch.energy_saver.remote

data class ErrorResponse(
    val statusMessage: String
) : Exception()
