package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class PayType {
    HOME,
    ENTE,
    FOOD,
    MONEY
}

@Parcelize
data class FixedPay (val type :PayType, val where:String, val money:Int,val go: Boolean):Parcelable

