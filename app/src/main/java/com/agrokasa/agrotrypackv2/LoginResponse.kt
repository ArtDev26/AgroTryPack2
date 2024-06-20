package com.agrokasa.agrotrypackv2

import com.google.gson.annotations.SerializedName

data class LoginResponse( //Clase que modela la respuesta API
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String?, // Mensaje opcional
    @SerializedName("token")
    val token: String? // Token de autenticación opcional
)