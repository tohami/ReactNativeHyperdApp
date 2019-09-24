package com.tohami.egyptinnovate.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsDetails {

    @SerializedName("NewsTitle")
    @Expose
    var newsTitle: String? = null
    @SerializedName("ItemDescription")
    @Expose
    var itemDescription: String? = null
    @SerializedName("Nid")
    @Expose
    var nid: String? = null
    @SerializedName("NumofViews")
    @Expose
    var numofViews: String? = null
    @SerializedName("Likes")
    @Expose
    var likes: String? = null
    @SerializedName("PostDate")
    @Expose
    var postDate: String? = null
    @SerializedName("ImageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("NewsType")
    @Expose
    var newsType: String? = null
    @SerializedName("ShareURL")
    @Expose
    var shareURL: String? = null
    @SerializedName("VideoURL")
    @Expose
    var videoURL: String? = null

}
