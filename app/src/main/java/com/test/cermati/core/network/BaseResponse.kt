package com.test.cermati.core.network

open class BaseResponse<T> {
    private val items: List<T>? = null

    fun data(): List<T>? {
        return items
    }
}