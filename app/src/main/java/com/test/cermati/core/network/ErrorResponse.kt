package com.test.cermati.core.network

data class ErrorResponse(
    var code: Int = 500,
    var message: String? = "Internal Server Error."
)