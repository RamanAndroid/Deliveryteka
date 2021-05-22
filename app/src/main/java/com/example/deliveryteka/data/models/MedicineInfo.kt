package com.example.deliveryteka.data.models

import android.os.Parcelable
import com.example.deliveryteka.utility.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class MedicineInfo(
    @SerializedName("medicine_id")
    @Expose
    val medicineId: String,

    @SerializedName("medicine_name")
    @Expose
    val medicineName: String,

    @SerializedName("medicine_price")
    @Expose
    val medicinePrice: String,

    @SerializedName("medicine_pack")
    @Expose
    val medicinePack: String,

    @SerializedName("medicine_dosage")
    @Expose
    val medicineDosage: String,

    @SerializedName("medicine_country")
    @Expose
    val medicineCountry: String,

    @SerializedName("medicine_description")
    @Expose
    val medicineDescription: String,

    @SerializedName("medicine_category")
    @Expose
    val medicineCategory: String,

    @SerializedName("medicine_form")
    @Expose
    val medicineForm: String,

    @SerializedName("medicine_img")
    @Expose
    val medicineImg: String,

    @SerializedName("count")
    @Expose
    val count: String?,

    @SerializedName("medicine_pdf")
    @Expose
    val medicinePDF: String
) : Parcelable {
    val attributionUrl get() = Constants.BASE_URL + medicineImg

    val attributionUrlPDF get() = Constants.BASE_URL + medicinePDF
}

