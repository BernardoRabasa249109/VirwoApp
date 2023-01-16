package com.benyfrabasa.app.presentation.dashboard.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benyfrabasa.app.databinding.ItemArticleBinding
import com.benyfrabasa.app.domain.model.Article
import javax.inject.Inject

class NewsAdapter @Inject constructor() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var data: List<Article> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = this.data.size

    fun setData(data: List<Article>) {
        this.data = data
        notifyItemRangeInserted(0, data.size)
    }

    class NewsViewHolder(
        private val binding: ItemArticleBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) = with(binding) {
            itemTitle.text = item.title
            itemAuthor.text = item.author
            itemDescription.text = item.description
        }
    }
}
