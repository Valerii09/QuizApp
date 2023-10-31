package com.quizeu.appitaly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quizeu.appitaly.R
import com.quizeu.appitaly.model.NewsItem

class NewsAdapter(private val newsList: List<NewsItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var expandedPosition = -1

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headlineTextView: TextView = itemView.findViewById(R.id.headlineTextView)
        val newsImageView: ImageView = itemView.findViewById(R.id.newsImageView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val sourceTextView: TextView = itemView.findViewById(R.id.sourceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.headlineTextView.text = news.headline
        holder.dateTextView.text = news.date
        holder.sourceTextView.text = news.source
        holder.descriptionTextView.text = news.description

        val isExpanded = position == expandedPosition
        holder.descriptionTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE

        // Используем библиотеку Glide для загрузки и установки изображения из URL
        Glide.with(holder.itemView)
            .load(news.imageUrl) // Загрузка изображения по URL
            .placeholder(R.drawable.placeholder_image) // Заглушка для показа, пока изображение загружается
            .into(holder.newsImageView) // Установка изображения в ImageView

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
}


