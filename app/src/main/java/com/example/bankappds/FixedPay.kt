package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FixedPay (val where:String, val money:Int):Parcelable

