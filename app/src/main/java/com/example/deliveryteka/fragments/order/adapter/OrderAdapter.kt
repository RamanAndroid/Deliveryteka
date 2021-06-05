package com.example.deliveryteka.fragments.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryteka.data.models.OrdersItem
import com.example.deliveryteka.databinding.ItemOrderBinding

class OrderAdapter(
    private val listenerOpen: OnOpenClickListener
) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    var dataList = emptyList<OrdersItem>()

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.checkOrder.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = dataList[position]
                    listenerOpen.onOpenItemClick(item)
                }

            }
        }

        fun bind(order: OrdersItem) {
            binding.apply {

                val textCount = StringBuilder("Заказ №${order.order_id} от:").toString()
                val textTime = StringBuilder(order.order_datetime).toString()
                val textStatus = StringBuilder(order.order_status).toString()
                val textPrice = StringBuilder("${order.order_total} бел.руб.").toString()
                val textPayMethod = StringBuilder(order.pay_method).toString()

                orderCounter.text = textCount
                orderTime.text = textTime
                orderStatus.text= textStatus
                orderSum.text = textPrice
                orderPayMethod.text=textPayMethod
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(order: List<OrdersItem>) {
        val toDoDiffUtil = OrderDiffUtil(dataList, order)
        val toDiffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = order
        toDiffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnOpenClickListener {
        fun onOpenItemClick(order: OrdersItem)
    }
}