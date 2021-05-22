package com.example.deliveryteka.fragments.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryteka.data.models.CategoriesItem
import com.example.deliveryteka.databinding.ItemCatalogBinding


class CatalogAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    var dataList = emptyList<CategoriesItem>()

    inner class CatalogViewHolder(private val binding: ItemCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.layoutItemCatalog.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = dataList[position]
                    listener.onItemClick(item)
                }

            }
        }

        fun bind(catalogData: CategoriesItem) {
            binding.catalogData = catalogData
            binding.executePendingBindings()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = ItemCatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CatalogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    interface OnItemClickListener {
        fun onItemClick(categoriesItem: CategoriesItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(categoriesData: List<CategoriesItem>) {
        val toDoDiffUtil = CatalogDiffUtil(dataList, categoriesData)
        val toDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = categoriesData
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

}