package com.quizeu.appitaly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val newsList: List<NewsItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.headlineTextView.text = news.headline
        holder.dateTextView.text = news.date
        holder.sourceTextView.text = news.source
        Glide.with(holder.itemView).load(news.imageUrl).into(holder.newsImageView)

        val isExpanded = position == expandedPosition

        if (isExpanded) {
            holder.descriptionTextView.visibility = View.VISIBLE
        } else {
            holder.descriptionTextView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            notifyDataSetChanged()
            listener.onItemClick(news)
        }
    }



    override fun getItemCount(): Int {
        return newsList.size
    }

    interface OnItemClickListener {
        fun onItemClick(newsItem: NewsItem)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headlineTextView: TextView = itemView.findViewById(R.id.headlineTextView)
        val newsImageView: ImageView = itemView.findViewById(R.id.newsImageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val sourceTextView: TextView = itemView.findViewById(R.id.sourceTextView)
    }
}

