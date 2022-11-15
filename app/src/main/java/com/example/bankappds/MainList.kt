package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

/* data class MainList(val type:String, val money: Int) : Parcelable */



data class MainList(val year: Int,
               val month: Int,
               val day: Int,
               val expense: Int,
               val category: String,
               val memo: String ): Parcelable

