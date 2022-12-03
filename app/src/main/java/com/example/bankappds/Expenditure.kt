package com.example.bankappds

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import org.checkerframework.checker.units.qual.K

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
data class Expenditure(
    var year: Int = 0,
    var month: Int = 0,
    val day: Int,
    val expense: Int,
    val category: Ecategory?,
    val memo: String ): Parcelable
{
    //TODO hashmap -> 클래스로 바꾸는 함수를
}


@Keep
data class Test(
    var ExpenditureMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
)


// cloud 에서 데이터 가져오기 위한 클래스
// deserialize 역직렬화 : 어떤 외부 파일의 데이터를 프로그램 내의 object로 read 해오는 것
// no-argument constructor : 기본 생성자
data class ExpMap(
    val TotalExpense: Int = 0,
    val Email: String = "",
    val ExpenditureMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf(),
    val RegTotalExpense: Int = 0,
    val Name: String = "",
    val Password: String = ""
)
