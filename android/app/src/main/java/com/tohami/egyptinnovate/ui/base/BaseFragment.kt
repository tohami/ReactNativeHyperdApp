package com.tohami.egyptinnovate.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.snackbar.Snackbar

import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    protected abstract val layoutId: Int

    protected abstract fun initializeViews(view: View)

    protected abstract fun setListeners()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container , false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        setListeners()
    }

    protected fun showSimpleSnack(msg: String) {
        if (view != null)
            Snackbar.make(view!!, msg, Snackbar.LENGTH_SHORT)
                    .show()
    }
}
