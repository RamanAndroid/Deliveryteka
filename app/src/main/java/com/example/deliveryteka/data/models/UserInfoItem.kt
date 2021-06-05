package com.example.deliveryteka.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoItem(
    val med_card_number: String,
    val user_address: String,
    val user_name: String,
    val user_phone: String
) : Parcelable