package com.example.deliveryteka.fragments.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryteka.data.models.CategoriesItem
import com.example.deliveryteka.databinding.ItemCatalogBinding


class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    var dataList = emptyList<CategoriesItem>()

    class CatalogViewHolder(private val binding: ItemCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(catalogData: CategoriesItem) {
            binding.catalogData = catalogData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CatalogViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCatalogBinding.inflate(layoutInflater, parent, false)
                return CatalogViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(categoriesData: List<CategoriesItem>) {
        val toDoDiffUtil = CatalogDiffUtil(dataList,categoriesData)
        val toDiffUtilResult= DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = categoriesData
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

}