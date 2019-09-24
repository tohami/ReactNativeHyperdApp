package com.tohami.egyptinnovate.ui.news.details.data

import com.tohami.egyptinnovate.data.model.GetNewsDetailsResponse
import com.tohami.egyptinnovate.data.retrofit.ApiInterface
import com.tohami.egyptinnovate.ui.base.BaseRepository

import javax.inject.Inject

import io.reactivex.Single

class NewsDetailsRepository @Inject
constructor(newsApi: ApiInterface) : BaseRepository(newsApi) {

    fun getNewsDetails(id: String): Single<GetNewsDetailsResponse> {
        return newsApi.getNewsDetails(id)
    }
}