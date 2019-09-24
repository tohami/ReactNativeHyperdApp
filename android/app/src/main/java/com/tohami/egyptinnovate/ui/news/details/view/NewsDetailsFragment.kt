package com.tohami.egyptinnovate.ui.news.details.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.tohami.egyptinnovate.R
import com.tohami.egyptinnovate.data.model.DataModel
import com.tohami.egyptinnovate.data.model.NewsDetails
import com.tohami.egyptinnovate.ui.base.BaseFragment
import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModel
import com.tohami.egyptinnovate.ui.news.details.viewModel.NewsDetailsViewModelFactory
import com.tohami.egyptinnovate.utilities.Constants
import com.tohami.egyptinnovate.utilities.ifNull
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_news_details.*
import javax.inject.Inject


class NewsDetailsFragment : BaseFragment() {
    
    @Inject
    lateinit var newsDetailsViewModelFactory: NewsDetailsViewModelFactory

    private var shareIntent: ShareCompat.IntentBuilder? = null

    override val layoutId: Int
        get() = R.layout.fragment_news_details

    private val getNewsDetailsConsumer = Consumer<DataModel<NewsDetails>> { detailsDataModel ->
        when (detailsDataModel.status) {
            Constants.Status.SUCCESS -> {
                news_details_container.visibility = View.VISIBLE
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.GONE
                updateUi(detailsDataModel.responseBody)
            }
            Constants.Status.FAIL -> {
                news_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.text = detailsDataModel.errorMsg
            }
            Constants.Status.NO_NETWORK -> {
                news_details_container.visibility = View.GONE
                msg_container.visibility = View.VISIBLE
                progress_layout.visibility = View.GONE
                tv_msg.setText(R.string.error_no_internet_connection)
            }
            Constants.Status.LOADING -> {
                msg_container.visibility = View.GONE
                progress_layout.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_news_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_share) {
            shareIntent?.startChooser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val newsId = getNewsIdFromBundle(arguments)

        val mViewModel = ViewModelProviders.of(this, newsDetailsViewModelFactory)
                .get(NewsDetailsViewModel::class.java)

        mViewModel.getNewsDetails(newsId).subscribe(getNewsDetailsConsumer)
    }

    private fun getNewsIdFromBundle(args: Bundle?): String {
        return args?.getString(Constants.News.ARGS_NEWS_ID , "-1")?: "-1"
    }

    private fun updateUi(details: NewsDetails?) {
        details?.run {
            tv_news_description.text = itemDescription
            tv_news_date.text = postDate
            tv_news_likes.text = getString(R.string.likes, likes)
            tv_news_views.text = getString(R.string.views, numofViews)
            if (imageUrl.isNullOrEmpty())
                iv_news_image.setImageResource(R.drawable.details_placeholder)
            else {
                Picasso.get().load(imageUrl)
                        .error(R.drawable.news_temp_image)
                        .placeholder(R.drawable.news_temp_image)
                        .into(iv_news_image)
            }
            shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_article_chooser_header))
                    .setText(details.shareURL)
        }.ifNull {
            news_details_container.visibility = View.GONE
            msg_container.visibility = View.VISIBLE
            tv_msg.setText(R.string.somethingWrong)
        }
    }

    override fun initializeViews(view: View) {
    }

    override fun setListeners() {
    }

}
