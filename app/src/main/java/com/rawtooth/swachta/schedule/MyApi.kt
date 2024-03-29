package com.rawtooth.swachta.schedule


import com.rawtooth.swachta.Constant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface MyApi {
    @Multipart
    @POST("waste/")
    fun uplodImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("wasteImage") wasteImage: RequestBody,
        @Part("name") name:RequestBody,
        @Part("userId") id: RequestBody,
        ): Call<String>
    companion object{
        operator fun invoke():MyApi{
            return Retrofit.Builder()
                .baseUrl(Constant.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}