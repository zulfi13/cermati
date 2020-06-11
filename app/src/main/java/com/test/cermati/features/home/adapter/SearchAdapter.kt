package com.test.cermati.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.test.cermati.core.ui.BaseAdapter
import com.test.cermati.core.model.Search
import com.test.cermati.databinding.ItemSearchBinding

class SearchAdapter (
    val onClick: (Search) -> Unit
) : BaseAdapter<SearchAdapter.ViewHolder, Search>(){

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val promo = models[position]
        holder.apply {
            bind(promo)
            itemView.setOnClickListener { onClick(promo) }
        }
    }

    class ViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }
    }
}

