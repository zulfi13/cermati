package com.test.cermati.core.model

data class DialogPopUp(
    val image: Int,
    val title: String,
    val body: String = "",
    val button: String = "BACK"
)