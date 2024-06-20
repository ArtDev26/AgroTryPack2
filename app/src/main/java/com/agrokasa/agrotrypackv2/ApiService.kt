package com.agrokasa.agrotrypackv2

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService { //Define metodos y endpoints del API
    @FormUrlEncoded
    @POST("/api/login/authenticate")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
