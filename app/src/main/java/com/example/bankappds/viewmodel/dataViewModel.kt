package com.example.bankappds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class dataViewModel: ViewModel() {
    // 뷰모델은 데이터 자료구조를 가지고 있음
    // 뷰모델은 프레그먼트를 직접 보지 않는다.
    // mbti를 읽으면 _mbti가 읽힘


    // 내부적으로는 바꿀수있는 라이브데이터
    private val _expense = MutableLiveData<Int>(0)
    // 하지만 밖에서는 바꿀수없는 라이브데이터 - 일종의 패턴임임
    val expense : LiveData<Int> get() = _expense

    fun plusExpense(data: Int) {
        _expense.value = data
    }
    fun setZero() {
        _expense.value = 0
    }

}