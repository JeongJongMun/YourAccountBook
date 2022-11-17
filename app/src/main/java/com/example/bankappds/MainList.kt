package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class Ecategory {
    FOOD,
    ENTERTAINMENT,
    SHOPPING,
    HOBBY,
    HEALTH,
    FINANCE,
    HOME,
    ETC
}


@Parcelize
data class MainList(val year: Int,
               val month: Int,
               val day: Int,
               val expense: Int,
               val category: Ecategory,
               val memo: String ): Parcelable

