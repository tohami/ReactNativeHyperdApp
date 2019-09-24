package com.tohami.egyptinnovate.data.retrofit

import com.tohami.egyptinnovate.data.model.GetNewsDetailsResponse
import com.tohami.egyptinnovate.data.model.GetNewsResponse

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @get:GET("safe/GetNews")
    val newsList: Single<GetNewsResponse>

    @GET("safe/GetNewsDetails")
    fun getNewsDetails(@Query("nid") newsId: String): Single<GetNewsDetailsResponse>
}
