package com.example.deliveryteka.data.models

import com.example.deliveryteka.utility.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BasketMedicineItem(
    val count: String,

    @SerializedName("medicine_img")
    @Expose
    val img: String,
    val medicine_country: String,
    val medicine_description: String,
    val medicine_dosage: String,
    val medicine_form: String,
    val medicine_id: String,
    val medicine_name: String,
    val medicine_pack: String,
    val medicine_price: String,
    val sum: String
){
    val attributionUrl get() = Constants.BASE_URL + img
}