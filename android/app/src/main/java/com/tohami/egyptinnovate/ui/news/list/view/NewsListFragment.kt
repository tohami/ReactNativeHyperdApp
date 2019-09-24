package com.tohami.egyptinnovate.ui.news.list.view

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.tohami.egyptinnovate.R
import com.tohami.egyptinnovate.data.model.DataModel
import com.tohami.egyptinnovate.data.model.NewsItem
import com.tohami.egyptinnovate.ui.base.BaseFragment
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModel
import com.tohami.egyptinnovate.ui.news.list.viewModel.NewsListViewModelFactory
import com.tohami.egyptinnovate.utilities.Constants
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_news_list.*
import java.util.*
import javax.inject.Inject


class NewsListFragment : BaseFragment(), NewsListAdapter.OnItemClickedListener {

    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var mViewModel: NewsListViewModel

    @Inject
    lateinit var viewModelFactory: NewsListViewModelFactory

    override val layoutId: Int
        get() = R.layout.fragment_news_list


    private val getNewsConsumer = Consumer<DataModel<List<NewsItem>>> { listDataModel ->
        when (listDataModel.status) {
            Constants.Status.SUCCESS -> {
                rv_news.visibility = View.VISIBLE
                msg_container.visibility = View.GONE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                newsListAdapter.replaceItems(listDataModel?.responseBody?: emptyList())
            }
            Constants.Status.FAIL -> {
                rv_news.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                tv_msg.text = listDataModel.errorMsg
            }
            Constants.Status.NO_NETWORK -> {
                rv_news.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                refresh_layout.isRefreshing = false
                progress_layout.visibility = View.GONE
                tv_msg.setText(R.string.error_no_internet_connection)
            }
            Constants.Status.LOADING -> {
                if (!refresh_layout.isRefreshing) {
                    progress_layout.visibility = View.VISIBLE
                }
                msg_container.visibility = View.GONE
                newsListAdapter.replaceItems(emptyList())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_news_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_filter) {
            showSimpleSnack(getString(R.string.filter_clicked))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(NewsListViewModel::class.java)

        mViewModel.newsSubject.subscribe(getNewsConsumer)
    }

    override fun initializeViews(view: View) {
        newsListAdapter = NewsListAdapter(ArrayList(), this)
        rv_news.adapter = newsListAdapter
    }

    override fun setListeners() {
        refresh_layout.setOnRefreshListener { mViewModel.refreshNewsList() }

    }


    override fun onItemClicked(view: View, item: NewsItem) {
        val args = Bundle().apply {
            putString(Constants.News.ARGS_NEWS_ID, item.nid)
        }
        Navigation
                .findNavController(view)
                .navigate(R.id.action_fragmentNewsList_to_fragmentNewsDetails, args)
    }

}
