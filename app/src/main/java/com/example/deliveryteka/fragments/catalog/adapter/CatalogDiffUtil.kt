package com.example.deliveryteka.fragments.catalog.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryteka.data.models.CategoriesItem

class CatalogDiffUtil(
        private val oldList: List<CategoriesItem>,
        private val newList: List<CategoriesItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].category==newList[newItemPosition].category
    }

}