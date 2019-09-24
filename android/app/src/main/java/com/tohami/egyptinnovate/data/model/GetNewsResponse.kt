package com.tohami.egyptinnovate.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetNewsResponse {

    @SerializedName("News")
    @Expose
    var newsItems: List<NewsItem>? = null

}
