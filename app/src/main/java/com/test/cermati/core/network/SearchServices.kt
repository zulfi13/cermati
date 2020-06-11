package com.test.cermati.core.network

import com.test.cermati.core.model.Search
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchServices {

    companion object {
        private var instance: SearchServices? = null

        fun instance() : SearchServices{
            instance?.let {
                return it
            }?: return createInstance()
        }

        private fun createInstance(): SearchServices{
            instance = NetworkClient.createService(SearchServices::class.java)
            return instance!!
        }

        fun removeInstance(){
            instance = null
        }
    }

    @GET("search/users")
    fun getUserByName(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int = 15
    ): Single<BaseResponse<Search>>
}