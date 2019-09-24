package com.tohami.egyptinnovate.ui.navigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tohami.egyptinnovate.R
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*


class NavigationDrawerFragment(private val onClickListener: View.OnClickListener) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawer_events.setOnClickListener(onClickListener)
        drawer_leadership.setOnClickListener(onClickListener)
        drawer_language.setOnClickListener(onClickListener)
        drawer_map.setOnClickListener(onClickListener)
        drawer_news.setOnClickListener(onClickListener)
    }
}
