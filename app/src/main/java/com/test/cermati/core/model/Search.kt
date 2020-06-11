package com.test.cermati.core.model

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("id")                   val id: Int?,
    @SerializedName("avatar_url")           val avatar: String?,
    @SerializedName("login")                val name: String
)