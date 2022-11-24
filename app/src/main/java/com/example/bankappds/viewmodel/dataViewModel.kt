package com.example.bankappds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankappds.Expenditure

class dataViewModel: ViewModel() {
    // 뷰모델은 데이터 자료구조를 가지고 있음
    // 뷰모델은 프레그먼트를 직접 보지 않는다.
    // mbti를 읽으면 _mbti가 읽힘


    // 내부적으로는 바꿀수있는 라이브데이터
    private val _expense = MutableLiveData<Int>(0)
    // 하지만 밖에서는 바꿀수없는 라이브데이터 - 일종의 패턴임임
    val expense : LiveData<Int> get() = _expense

    private val _expenditure = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val expenditure : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _expenditure

    var tempMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()

    fun plusExpense(data: Int) {
        _expense.value = data
    }

    fun addExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        // 기존 값 존재
        if (expenditure.value?.get(dayInfo) != null) {
            tempMap[dayInfo]?.add(expd)
            _expenditure.value = tempMap
            println("Add Expenditure ${_expenditure.value}")
        } else { // 기존 값 존재 X
            tempMap[dayInfo] = mutableListOf(expd)
            _expenditure.value = tempMap
            println("Add Expenditure ${_expenditure.value}")
        }
    }
    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }



}
