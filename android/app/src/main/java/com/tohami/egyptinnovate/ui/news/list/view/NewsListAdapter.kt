package com.tohami.egyptinnovate.ui.news.list.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tohami.egyptinnovate.R
import com.tohami.egyptinnovate.data.model.NewsItem
import com.tohami.egyptinnovate.utilities.Constants
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

class NewsListAdapter(list: List<NewsItem>, private val onItemClickListener: OnItemClickedListener) : RecyclerView.Adapter<NewsListAdapter.NewsItemHolder>() {

    private val newsItemList: MutableList<NewsItem>

    init {
        newsItemList = ArrayList(list)
    }

    fun addItems(list: List<NewsItem>) {
        newsItemList.addAll(list)
        notifyDataSetChanged()
    }

    fun replaceItems(list: List<NewsItem>) {
        newsItemList.clear()
        newsItemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        newsItemList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsItemHolder(root)
    }

    override fun onBindViewHolder(holder: NewsItemHolder, position: Int) {
        holder.bind(if (newsItemList.size > position) newsItemList[position] else null)
    }

    /**
     * View Holder for a [NewsItem] RecyclerView list item.
     */
    inner class NewsItemHolder internal constructor(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        internal fun bind(item: NewsItem?) {

            item?.let { news ->
                itemView.run {
                    tv_news_title.text = news.newsTitle
                    tv_news_date.text = news.postDate
                    tv_news_likes.text = itemView.context.getString(R.string.likes, news.likes)
                    tv_news_views.text = itemView.context.getString(R.string.views, news.numofViews)

                    if (news.imageUrl == null || news.imageUrl!!.isEmpty())
                        iv_news_image.setImageResource(R.drawable.news_temp_image)
                    else {
                        Picasso.get().load(news.imageUrl)
                                .error(R.drawable.news_temp_image)
                                .placeholder(R.drawable.news_temp_image)
                                .into(iv_news_image)
                    }

                    if (news.newsType == Constants.News.TYPE_ARTICLE) {
                        iv_news_type_label.setImageResource(R.drawable.news_article_label)
                    } else {
                        iv_news_type_label.setImageResource(R.drawable.news_video_label)
                    }
                    animateViewHolder()
                }
            }
        }

        private fun animateViewHolder() {
            val itemAnimator = AnimationUtils.loadLayoutAnimation(itemView.context, R.anim.partial_in_from_left_animation)
            val labelAnimator = AnimationUtils.loadAnimation(itemView.context, R.anim.partial_in_from_right)
            itemView.startAnimation(itemAnimator.animation)
            itemView.iv_news_type_label.startAnimation(labelAnimator)
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        override fun onClick(view: View) {
            if (adapterPosition > RecyclerView.NO_POSITION && adapterPosition < newsItemList.size) {
                onItemClickListener.onItemClicked(view, newsItemList[adapterPosition])
            }
        }
    }


    interface OnItemClickedListener {
        fun onItemClicked(view: View, item: NewsItem)
    }
}