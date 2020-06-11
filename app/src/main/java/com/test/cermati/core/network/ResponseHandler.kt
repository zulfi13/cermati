package com.test.cermati.core.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.test.cermati.core.util.Constants
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class ResponseSingle <T> constructor(
    private val compositeDisposable: CompositeDisposable,
    private val dontThrowErrorIsEmpty: Boolean = false
): SingleObserver<BaseResponse<T>> {

    private val serviceResponseHandler = ServiceResponseHandler<T>()

    override fun onSuccess(response: BaseResponse<T>) {
        val data = serviceResponseHandler.handleResponseSuccess(response)
        data?.let {
            onResponseSuccess(it)
        } ?: run {
            if (dontThrowErrorIsEmpty)
                onResponseSuccess(null)
            else
                onResponseError("Something Wrong", 400)
        }
    }

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        val error = ResponseErrorHandler().handleResponseError(e)
        onResponseError("${error.message}", error.code)
    }

    abstract fun onResponseSuccess(data: List<T>?)
    abstract fun onResponseError(msg: String, code: Int)
}

class ResponseErrorHandler {
    fun handleResponseError(e: Throwable) : ErrorResponse{
        e.printStackTrace()
        val error = ErrorResponse()
        when (e) {
            is HttpException -> {
                //Have Error Message
                val responseBody = (e).response().errorBody()
                val errorResponse = parseError(responseBody)
                if (errorResponse != null) {
                    when(e.code()){
                        302 ->
                            error.message = "Hotspot login required"
                        else ->
                            error.message = errorResponse.message ?: e.message()
                    }
                    error.code = errorResponse.code
                }else{
                    error.message = "Something wrong with our server. Please try again later."
                }
            }
            is JsonSyntaxException ->
                //Error Parsing
                error.message = "Something wrong with response data."
            is SocketTimeoutException ->
                //Network Timeout
                error.message = "Request Timeout."
            is IOException ->
                //Network Error
                error.message = "Anda tidak terhubung dengan jaringan. Silakan coba beberapa saat lagi."
            else -> {
                //General Error
                error.code = 599
                error.message = "Anda tidak terhubung dengan jaringan. Silakan coba beberapa saat lagi."
            }
        }

        return error

    }


    private fun parseError(errorBody: ResponseBody?): ErrorResponse? {
        val converter = NetworkClient.retrofit
            .responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0))

        val error: ErrorResponse?

        try {
            error = converter.convert(errorBody!!)
        } catch (e: IOException) {
            return ErrorResponse()
        }

        return error
    }
}

class ServiceResponseHandler<T> {

    fun handleResponseSuccess(response: BaseResponse<T>) : List<T>? {
        val gson = GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting().create()
        try { Constants.log("[SERVICE_RESPONSE_OK] " + "\n" +
                "response body : " + gson.toJson(response.data()) ) }
        catch (e: Exception){}

        return response.data()
    }
}