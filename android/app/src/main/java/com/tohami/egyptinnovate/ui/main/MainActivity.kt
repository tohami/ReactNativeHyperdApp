package com.tohami.egyptinnovate.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.tohami.egyptinnovate.R
import com.tohami.egyptinnovate.app.localization.Language
import com.tohami.egyptinnovate.ui.base.BaseActivity
import com.tohami.egyptinnovate.ui.navigation.NavigationDrawerFragment
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModel
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import javax.inject.Inject

class MainActivity : BaseActivity(), DefaultHardwareBackBtnHandler {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: NewsListViewModelFactory

    private val onNavigationItemClicked = View.OnClickListener { view ->
        when (view.id) {
            R.id.drawer_events -> navController.navigate(R.id.action_fragmentNewsList_to_RNHelloFragment)

            R.id.drawer_news -> showSimpleSnack(getString(R.string.news_clicked))

            R.id.drawer_language -> {
                if (appSettings.currentLanguage == Language.ENGLLSH) {
                    appSettings.currentLanguage = Language.ARABIC
                } else {
                    appSettings.currentLanguage =Language.ENGLLSH
                }
                finish()
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
            }

            R.id.drawer_leadership -> showSimpleSnack(getString(R.string.leadership_clicked))

            R.id.drawer_map -> showSimpleSnack(getString(R.string.maps_clicked))
        }
        drawer_layout.closeDrawers()
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override val containReactView: Boolean
            get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsListViewModel::class.java)

        if (savedInstanceState == null)
            mViewModel.refreshNewsList()
    }

    override fun initializeViews() {
        setToolbar(toolbar, R.color.colorPrimary, getString(R.string.app_name), showUpButton = true, withElevation = false)
        navController = Navigation.findNavController(this, R.id.main_nav_container)

    }

    private fun setUpNavDrawerListeners() {
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, myToolbar, R.string.app_name, R.string.app_name) {

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_fragment_container, NavigationDrawerFragment(onNavigationItemClicked))
                .commit()

        mDrawerToggle.setToolbarNavigationClickListener { navController.popBackStack() }

        drawer_layout.addDrawerListener(mDrawerToggle)
    }

    private fun enableDisableNavDrawer(enable: Boolean) {
        mDrawerToggle.isDrawerIndicatorEnabled = enable
        drawer_layout.setDrawerLockMode(if (enable) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        mDrawerToggle.syncState()
    }

    override fun setListeners() {
        setUpNavDrawerListeners()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.fragmentNewsList -> enableDisableNavDrawer(true)
                R.id.fragmentNewsDetails -> enableDisableNavDrawer(false)
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        mDrawerToggle.syncState()
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }
}
