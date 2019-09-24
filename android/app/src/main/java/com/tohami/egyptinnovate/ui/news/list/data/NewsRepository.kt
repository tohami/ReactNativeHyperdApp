package com.tohami.egyptinnovate.ui.news.list.data

import com.tohami.egyptinnovate.data.model.GetNewsResponse
import com.tohami.egyptinnovate.data.retrofit.ApiInterface
import com.tohami.egyptinnovate.ui.base.BaseRepository

import javax.inject.Inject

import io.reactivex.Single

class NewsRepository @Inject
constructor(newsApi: ApiInterface) : BaseRepository(newsApi) {

    val news: Single<GetNewsResponse>
        get() = newsApi.newsList
}