package com.rawtooth.swachta

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface localHost {
    //http://localhost:9090/user/

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("/user")
    fun sendUserData(@Body userPost:RegisterPost, @Header("Content-Length") length : Int ):Call<RegisterPost>
}