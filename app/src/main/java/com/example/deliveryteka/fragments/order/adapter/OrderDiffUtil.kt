package com.example.deliveryteka.fragments.order.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.deliveryteka.data.models.MedicineInfo
import com.example.deliveryteka.data.models.OrdersItem

class OrderDiffUtil (
    private val oldList: List<OrdersItem>,
    private val newList: List<OrdersItem>
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
        return oldList[oldItemPosition].order_id == newList[newItemPosition].order_id
                && oldList[oldItemPosition].order_datetime == newList[newItemPosition].order_datetime
    }
}