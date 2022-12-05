package com.example.bankappds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankappds.Ecategory
import com.example.bankappds.Expenditure
import com.example.bankappds.repository.Repository

class DataViewModel: ViewModel() {
    // 뷰모델은 데이터 자료구조를 가지고 있음
    // 뷰모델은 프레그먼트를 직접 보지 않는다.
    // mbti를 읽으면 _mbti가 읽힘
    private val repository = Repository()

    private val _email = MutableLiveData<String>("")
    val email : LiveData<String> get() = _email

    private val _password = MutableLiveData<String>("")
    val password : LiveData<String> get() = _password

    private val _name = MutableLiveData<String> ("")
    val name : LiveData<String> get() = _name

    private val _goalExpense = MutableLiveData<Int>(0)
    val goalExpense : LiveData<Int> get() = _goalExpense
    // 내부적으로는 바꿀수있는 라이브데이터
    private val _totalExpense = MutableLiveData<Int>(0)
    // 하지만 밖에서는 바꿀수없는 라이브데이터 - 일종의 패턴임임
    val totalExpense : LiveData<Int> get() = _totalExpense

    private val _totalRegExpense = MutableLiveData<Int>(0)
    val totalRegExpense : LiveData<Int> get() = _totalRegExpense


    private val _expenditureMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val expenditureMap : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _expenditureMap

    private val _regExpdMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val regExpdMap : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _regExpdMap

    var tempExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
    var tempRegExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()

    init { // realtime 에서 데이터 가져오기
        repository.getRealTimeEmail(_email)
        repository.getRealTimeName(_name)
        repository.getRealTimeTotalExpense(_totalExpense)
        repository.getRealTimeTotalRegExpense(_totalRegExpense)
        repository.getRealTimeExpendtureMap(_expenditureMap)
        repository.getRealTimeRegExpendtureMap(_regExpdMap)
    }

    // 목표 금액 설정
    fun setGoal(exp : Int) {
        _goalExpense.value = exp
    }
    // 로그인시 viewModel, realtime에 저장
    fun getPrivacy(email: String, password: String, name: String) {
        _email.value = email
        _password.value = password
        _name.value = name
        repository.postPrivacy(_email.value.toString(), _password.value.toString(), _name.value.toString())
    }

    // 연, 월, 일 합치기
    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }
    fun addExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (expenditureMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempExpdMap[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            tempExpdMap[dayInfo] = mutableListOf(expd)
        }

        //TODO 여기서 총지출 넘기는 거 관리
        _expenditureMap.value = tempExpdMap
        _totalExpense.value = _totalExpense.value?.plus(expd.expense)
        // realtime 에 저장
        repository.postExpenditureMap(email.value.toString(), _expenditureMap.value)
        // realtime, cloud 에 저장
        repository.postTotalExpense(email.value.toString(), _totalExpense.value ?: 0)
    }

    fun deleteExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempExpdMap[dayInfo]?.remove(expd)
        _expenditureMap.value = tempExpdMap
        _totalExpense.value = _totalExpense.value?.minus(expd.expense)

        // realtime 에 저장
        repository.postExpenditureMap(email.value.toString(), _expenditureMap.value)
        // cloud, realtime 에 저장
        repository.postTotalExpense(email.value.toString(), _totalExpense.value ?: 0)
    }


    fun addRegExpenditure(expd: Expenditure){
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (regExpdMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempRegExpdMap[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            tempRegExpdMap[dayInfo] = mutableListOf(expd)
        }
        _regExpdMap.value = tempRegExpdMap
        // realtime 에 저장
        repository.postRegExpenditureMap(_regExpdMap.value, _email.value.toString())
        _totalRegExpense.value = _totalRegExpense.value?.plus(expd.expense)
        // cloud, realtime 에 저장
        repository.postTotalRegExpense(_email.value.toString(), _totalRegExpense.value ?: 0)
    }
    fun deleteRegExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempRegExpdMap[dayInfo]?.remove(expd)
        _regExpdMap.value = tempExpdMap
        // realtime에 저장
        repository.postRegExpenditureMap(_regExpdMap.value, _email.value.toString())
        _totalRegExpense.value = _totalRegExpense.value?.minus(expd.expense)
        // cloud, realtime 에 저장
        repository.postTotalRegExpense(_email.value.toString(), _totalRegExpense.value ?: 0)
    }

    // 차트에서 카테고리별 보여주기
    fun getArraybyCategory(categoryType: Ecategory) : Int {
        var sumExpenditure : Int = 0

        if (expenditureMap.value != null){
            for ((K,V) in expenditureMap.value!!) {
                for (e in V) {
                    if (e.category == categoryType && e.expense != 0) {
                        sumExpenditure += e.expense
                    }
                }
            }
        }
        if (regExpdMap.value != null) {
            for ((K,V) in regExpdMap.value!!) {
                for (e in V) {
                    if (e.category == categoryType && e.expense != 0) {
                        sumExpenditure += e.expense
                    }
                }
            }
        }

        return sumExpenditure
    }

    // 월별 지출 가져오기
    fun getMonthList(month: Int) : MutableList<Expenditure>{
        val temp = mutableListOf<Expenditure>()
        val tempMap = _expenditureMap.value?.toSortedMap()

        print(tempMap)
        if (tempMap != null){
            for ((K,V) in tempMap!!) {
                if (K.substring(4,6).toInt() == month) {
                    for (i in V) temp.add(i)
                }
            }
        }
        return temp
    }

}
