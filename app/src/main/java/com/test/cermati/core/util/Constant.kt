package com.test.cermati.core.util

import android.util.Log
import com.test.cermati.BuildConfig

object Constants {

    const val BASE_URL                        = BuildConfig.BASE_URL
    const val IS_PRODUCTION                   = BuildConfig.ENVIROMENT == "production"

    fun log(msg: String) {
        Log.i("Cermati", msg)
    }

}