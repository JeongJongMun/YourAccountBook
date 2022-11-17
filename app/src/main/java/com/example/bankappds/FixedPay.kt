package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class PayType {
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
data class FixedPay (val type :Ecategory, val where:String, val money:Int):Parcelable
