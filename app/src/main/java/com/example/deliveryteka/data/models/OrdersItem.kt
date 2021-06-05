package com.example.deliveryteka.data.models

data class OrdersItem(
    val order_datetime: String,
    val order_id: String,
    val order_status: String,
    val order_total: String,
    val pay_method: String
)