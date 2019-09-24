package com.tohami.egyptinnovate.data.model

import androidx.annotation.Nullable
import com.tohami.egyptinnovate.utilities.Constants

//mutable response body
class DataModel<T>
/**
 * generic data model to be used in communication between repo and viewModel
 * @param status the response status one on @[Constants.Status]
 * @param errorMsg the error message , will be null if response is success
 * @param responseBody the response , will be null if request failed
 */(val status: Constants.Status, @Nullable val errorMsg: String?, @Nullable val responseBody: T?)
