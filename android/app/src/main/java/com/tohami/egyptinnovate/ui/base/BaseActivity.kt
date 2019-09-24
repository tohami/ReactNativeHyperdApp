package com.tohami.egyptinnovate.ui.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.facebook.react.ReactInstanceManager
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.google.android.material.snackbar.Snackbar
import com.tohami.egyptinnovate.R
import com.tohami.egyptinnovate.app.InnovateApplication
import com.tohami.egyptinnovate.app.localization.LocalizationHelper
import com.tohami.egyptinnovate.app.settings.AppSettings
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity() , DefaultHardwareBackBtnHandler {

    protected var myToolbar: Toolbar? = null
    @Inject
    lateinit var appSettings: AppSettings

    protected abstract val layoutId: Int

    protected abstract val containReactView: Boolean

    private var mReactInstanceManager: ReactInstanceManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalizationHelper.changeAppLanguage(appSettings.currentLanguage.code, this)
        setContentView(layoutId)
        initializeViews()
        setListeners()

        if (containReactView)
            mReactInstanceManager = (application as InnovateApplication).reactNativeHost.reactInstanceManager

    }

    protected fun setToolbar(toolbar: Toolbar, toolbarBackgroundColor: Int, toolbarTitle: String, showUpButton: Boolean, withElevation: Boolean) {
        myToolbar = toolbar.apply {
            title = toolbarTitle
            setBackgroundColor(ContextCompat.getColor(this@BaseActivity, toolbarBackgroundColor))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
                elevation = resources.getDimension(R.dimen.row_item_margin_horizontal)
            }
        }
        setSupportActionBar(myToolbar)

        showUpActionButton(showUpButton)
    }

    fun setToolbarTitle(title: String) {
        myToolbar?.title = title
    }

    private fun showUpActionButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)

    }

    protected fun showSimpleSnack(msg: String) {
        Snackbar.make(findViewById<View>(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
                .show()
    }

    protected abstract fun initializeViews()

    protected abstract fun setListeners()

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        mReactInstanceManager?.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        mReactInstanceManager?.onHostResume(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()

        mReactInstanceManager?.onHostDestroy(this)
//        mReactRootView.unmountReactApplication()
    }

    override fun onBackPressed() {
        mReactInstanceManager?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mReactInstanceManager?.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager?.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }
}
