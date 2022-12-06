package com.example.bankappds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.collections.HashMap as HashMap

// 카테고리 Enum
enum class Ecategory {
    FOOD,
    ENTERTAIN,
    SHOPPING,
    HOBBY,
    HEALTH,
    FINANCE,
    HOME,
    ETC
}

@Parcelize
class Expenditure(
    var year: Int = 0,
    var month: Int = 0,
    var day: Int = 0,
    var expense: Int = 0,
    var category: Ecategory? = null,
    var memo: String = "" ) : Parcelable {

    constructor(hashMap: HashMap<Any, Any>) : this() {
        year = hashMap["year"].toString().toInt()
        month = hashMap["month"].toString().toInt()
        day = hashMap["day"].toString().toInt()
        expense = hashMap["expense"].toString().toInt()
        category = when (hashMap["category"]){
            "FOOD" ->Ecategory.FOOD
            "FINANCE" ->Ecategory.FINANCE
            "SHOPPING" ->Ecategory.SHOPPING
            "ENTERTAIN" ->Ecategory.ENTERTAIN
            "HOBBY" ->Ecategory.HOBBY
            "HEALTH" ->Ecategory.HEALTH
            "HOME" ->Ecategory.HOME
            "ETC" ->Ecategory.ETC
            else -> null
        }
        memo = hashMap["memo"].toString()
    }
}

// 랭킹을 위한 데이터 클래스
data class FireStoreData(
    var ExpMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf(),
    var Name : String? = null,
    var MonthExpense: Int = 0)