package com.quizeu.appitaly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

// NewsDetailFragment.kt
class NewsDetailFragment : Fragment() {

    companion object {
        fun newInstance(newsItem: NewsItem): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val args = Bundle()
            args.putParcelable("news_item", newsItem)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_news_detail, container, false)

        val headlineTextView: TextView = rootView.findViewById(R.id.detail_headline)
        val descriptionTextView: TextView = rootView.findViewById(R.id.detail_description)
        val dateTextView: TextView = rootView.findViewById(R.id.detail_date)
        val sourceTextView: TextView = rootView.findViewById(R.id.detail_source)

        val newsItem = arguments?.getParcelable<NewsItem>("news_item")

        headlineTextView.text = newsItem?.headline
        descriptionTextView.text = newsItem?.description
        dateTextView.text = "Date: " + newsItem?.date
        sourceTextView.text = "Source: " + newsItem?.source

        return rootView
    }
}