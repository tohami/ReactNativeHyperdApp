package com.tohami.egyptinnovate.ui.base

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(private val connectivityManager: ConnectivityManager) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected val isNetworkAvailable: Boolean
        get() {
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return if (activeNetInfo != null) {
                activeNetInfo.isAvailable && activeNetInfo.isConnected
            } else {
                false
            }
        }

    protected fun addDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        this.compositeDisposable.clear()
    }
}
