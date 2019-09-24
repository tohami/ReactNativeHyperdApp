package com.tohami.egyptinnovate.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetNewsDetailsResponse {

    @SerializedName("newsItem")
    @Expose
    var newsDetails: NewsDetails? = null

}
