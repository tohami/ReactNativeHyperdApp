package com.tohami.egyptinnovate.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.tohami.egyptinnovate.app.InnovateApplication
import dagger.android.support.DaggerFragment


abstract class BaseReactFragment : Fragment() {
    private var mReactRootView: ReactRootView? = null
    private var mReactInstanceManager: ReactInstanceManager? = null

    // This method returns the name of our top-level component to show
    abstract fun getMainComponentName(): String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mReactRootView = ReactRootView(context)
        mReactInstanceManager = (activity!!.application as InnovateApplication)
                .reactNativeHost
                .reactInstanceManager

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        return mReactRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mReactRootView!!.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null
        )
    }
}
