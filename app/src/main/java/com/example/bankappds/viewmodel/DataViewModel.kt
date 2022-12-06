package com.example.bankappds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankappds.Ecategory
import com.example.bankappds.Expenditure
import com.example.bankappds.repository.Repository
import kotlinx.coroutines.launch

class DataViewModel: ViewModel() {
    // Firestore = 이름, 총지출, 고정지출
    // Realtime = 이름, 이메일, 비밀번호, 총지출맵, 고정지출맵, 총지출, 고정지출, 목표금액

    private val repository = Repository()

    // 내부적으로는 바꿀수있는 라이브데이터
    private val _email = MutableLiveData("")
    // 하지만 밖에서는 바꿀수없는 라이브데이터 - 일종의 패턴임임
    val email : LiveData<String> get() = _email

    private val _password = MutableLiveData("")

    private val _name = MutableLiveData("")
    val name : LiveData<String> get() = _name

    private val _goalExpense = MutableLiveData(0)
    val goalExpense : LiveData<Int> get() = _goalExpense
    private val _totalExpense = MutableLiveData(0)
    val totalExpense : LiveData<Int> get() = _totalExpense
    private val _totalRegExpense = MutableLiveData(0)
    val totalRegExpense : LiveData<Int> get() = _totalRegExpense


    private val _expenditureMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val expenditureMap : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _expenditureMap
    private val _regExpdMap = MutableLiveData<MutableMap<String, MutableList<Expenditure>>>()
    val regExpdMap : LiveData<MutableMap<String, MutableList<Expenditure>>> get() = _regExpdMap


    //환율 정보에 대한 데이터
    private val _exchangeDollarRate = MutableLiveData<Float>(0f)
    val exchangeDollarRate : LiveData<Float> get() = _exchangeDollarRate

    private val _exchangeEuroRate = MutableLiveData<Float>(0f)
    val exchangeEuroRate : LiveData<Float> get() = _exchangeEuroRate

    private val _exchangeYenRate = MutableLiveData<Float>(0f)
    val exchangeYenRate : LiveData<Float> get() = _exchangeYenRate



    var tempExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
    var tempRegExpdMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()





    init { // 앱 시작시 realtime 에서 데이터 가져오기
        repository.getRealTimeEmail(_email)
        repository.getRealTimeName(_name)
        repository.getRealTimeTotalExpense(_totalExpense)
        repository.getRealTimeTotalRegExpense(_totalRegExpense)
        repository.getRealTimeExpendtureMap(_expenditureMap)
        repository.getRealTimeRegExpendtureMap(_regExpdMap)
        repository.getRealTimeGoalExp(_goalExpense)
        retrieveAllRates()
    }


    fun retrieveExchangeDollarRate() {
        viewModelScope.launch {
            val temp = repository.readDollarExchangeRate()
            _exchangeDollarRate.value = temp
        }
    }
    fun retrieveExchangeEuroRate() {
        viewModelScope.launch {
            val temp = repository.readEuroExchangeRate()
            _exchangeEuroRate.value = temp
        }
    }
    fun retrieveExchangeYenRate() {
        viewModelScope.launch {
            val temp = repository.readYenExchangeRate()
            _exchangeYenRate.value = temp
        }
    }

    fun retrieveAllRates(){
        retrieveExchangeDollarRate()
        retrieveExchangeEuroRate()
        retrieveExchangeYenRate()
    }

    // 목표 금액 설정
    fun setGoal(exp : Int) {
        _goalExpense.value = exp
        repository.postGoalExpense(_goalExpense.value.toString().toInt())
    }
    // 로그인시 개인정보 viewModel, realtime에 저장
    fun privacy(email: String, password: String, name: String) {
        _email.value = email
        _password.value = password
        _name.value = name
        repository.postPrivacy(_email.value.toString(), _password.value.toString(), _name.value.toString())
    }

    // 연, 월, 일 Int 를 String 으로  합치기
    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }
    // Map에 Expenditure 객체 추가
    fun addExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (expenditureMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempExpdMap[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            tempExpdMap[dayInfo] = mutableListOf(expd)
        }
        // 뷰모델에 맵 저장
        _expenditureMap.value = tempExpdMap
        _totalExpense.value = _totalExpense.value?.plus(expd.expense)
        // 맵 = realtime 에 저장
        repository.postExpenditureMap(_expenditureMap.value)
        // 총지출 = realtime, cloud 에 저장
        repository.postTotalExpense(email.value.toString(), _totalExpense.value ?: 0)
    }

    // Map 에서 Expenditure 객체 삭제
    fun deleteExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempExpdMap[dayInfo]?.remove(expd)
        _expenditureMap.value = tempExpdMap
        _totalExpense.value = _totalExpense.value?.minus(expd.expense)

        // 맵 = realtime 에 저장
        repository.postExpenditureMap(_expenditureMap.value)
        // 총지출 = cloud, realtime 에 저장
        repository.postTotalExpense(email.value.toString(), _totalExpense.value ?: 0)
    }

    // 고정지출 맵에 Expenditure 객체 저장
    fun addRegExpenditure(expd: Expenditure){
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (regExpdMap.value?.get(dayInfo) != null) { // 기존 값 존재
            tempRegExpdMap[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            tempRegExpdMap[dayInfo] = mutableListOf(expd)
        }
        _regExpdMap.value = tempRegExpdMap
        // realtime 에 저장
        repository.postRegExpenditureMap(_regExpdMap.value)
        _totalRegExpense.value = _totalRegExpense.value?.plus(expd.expense)
        // cloud, realtime 에 저장
        repository.postTotalRegExpense(_email.value.toString(), _totalRegExpense.value ?: 0)
    }
    // 고정지출 맵에서 Expenditure 객체 삭제
    fun deleteRegExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)
        tempRegExpdMap[dayInfo]?.remove(expd)
        _regExpdMap.value = tempExpdMap
        // realtime에 저장
        repository.postRegExpenditureMap(_regExpdMap.value)
        _totalRegExpense.value = _totalRegExpense.value?.minus(expd.expense)
        // cloud, realtime 에 저장
        repository.postTotalRegExpense(_email.value.toString(), _totalRegExpense.value ?: 0)
    }

    // 차트에서 카테고리별 보여주기
    fun getArraybyCategory(categoryType: Ecategory) : Int {
        var sumExpenditure = 0

        val tempMap = expenditureMap.value
        // 지출 맵에서 가져오기
        if (tempMap != null){
            for ((K,V) in tempMap) {
                for (e in V) {
                    if (e.category == categoryType && e.expense != 0) {
                        sumExpenditure += e.expense
                    }
                }
            }
        }
        // 고정지출 맵에서 가져오기
        val tempRegMap = regExpdMap.value

        if (tempRegMap != null) {
            for ((K,V) in tempRegMap) {
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
    fun getMonthList(year: Int, month: Int) : MutableList<Expenditure>{
        val temp = mutableListOf<Expenditure>()
        val tempMap = _expenditureMap.value?.toSortedMap()

        if (tempMap != null){
            for ((K,V) in tempMap) {
                // 객체 별로 월 체크
                if (K.substring(0,4).toInt() == year && K.substring(4,6).toInt() == month) {
                    for (i in V) temp.add(i)
                }
            }
        }
        return temp
    }

    // 월별 총지출 가져오기
    fun getMonthExpense(year: Int ,month: Int) : Int{
        val tempMap = _expenditureMap.value?.toSortedMap()
        var totalExpense = 0
        if (tempMap != null){
            for ((K,V) in tempMap) {
                if (K.substring(0,4).toInt() == year && K.substring(4,6).toInt() == month) {
                    for ( expd in V) {
                        totalExpense += expd.expense
                    }
                }
            }
        }
        return totalExpense
    }

}
