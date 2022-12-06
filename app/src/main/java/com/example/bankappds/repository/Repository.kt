package com.example.bankappds.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bankappds.CardviewAdapter
import com.example.bankappds.Expenditure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class Repository {
    val database = Firebase.database
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    // realtime에서 데이터 가져올 변수들
    val emailRef = database.getReference("Email")
    val passwordRef = database.getReference("Password")
    val nameRef = database.getReference("Name")
    val expenditureMapRef = database.getReference("ExpenditureMap")
    val regExpenditureMapRef = database.getReference("RegExpenditureMap")
    val totalExpenseRef = database.getReference("TotalExpense")
    val totalRegExpenseRef = database.getReference("TotalRegExpense")
    val goalExpenseRef = database.getReference("GoalExpense")

    // 연, 월, 일을 합쳐서 String으로 변환
    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }

    // 앱 처음 실행시 realTime 에서 목표 금액 가져와 ViewModel 로 넘겨주기
    fun getRealTimeGoalExp(goal: MutableLiveData<Int>) {
        goalExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    goal.postValue(snapshot.value.toString().toInt())
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 서버에서 가져온 HashMap<ArrayList<HashMap>> 을 내부 데이터 형식으로 바꾸기
    fun addExpenditure(map: MutableMap<String, MutableList<Expenditure>>, expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (map[dayInfo] != null) { // 기존 값 존재
            map[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            map[dayInfo] = mutableListOf(expd)
        }
    }

    // 앱 처음 실행시 realtime 에서 지출Map 가져와 ViewModel 로 넘겨주기
    fun getRealTimeExpendtureMap(exp: MutableLiveData<MutableMap<String, MutableList<Expenditure>>>) {
        expenditureMapRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 서버에서 가져온 맵 ( HashMap<ArrayList<HashMap>> 형태로 저장됨
                val serverMap = snapshot.value as MutableMap<String, MutableList<HashMap<Any,Any>>>?
                // 내부 데이터 형식에 맞게 변환하기 위해 빈 맵 생성
                val changedServerMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
                if (serverMap != null) {
                    for ((K,V) in serverMap){
                        for (expd in V) {
                            // 빈 맵에 내부 데이터 형식에 맞게 바뀐 객체 집어넣기
                            addExpenditure(changedServerMap, Expenditure(expd))
                        }
                    }
                }
                exp.postValue(changedServerMap)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    // 앱 처음 실행시 realTime 에서 고정지출Map 가져와 ViewModel로 넘겨주기
    fun getRealTimeRegExpendtureMap(exp: MutableLiveData<MutableMap<String, MutableList<Expenditure>>>) {
        regExpenditureMapRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val serverMap = snapshot.value as MutableMap<String, MutableList<HashMap<Any,Any>>>?
                val changedServerMap: MutableMap<String, MutableList<Expenditure>> = mutableMapOf()
                if (serverMap != null) {
                    for ((K,V) in serverMap){
                        for (expd in V) {
                            addExpenditure(changedServerMap, Expenditure(expd))
                        }
                    }
                }
                exp.postValue(changedServerMap)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 앱 처음 실행시 realTime 에서 Email 가져와 ViewModel로 넘겨주기
    fun getRealTimeEmail(email: MutableLiveData<String>) {
        emailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 앱 처음 실행시 realTime 에서 Name 가져와 ViewModel로 넘겨주기
    fun getRealTimeName(name: MutableLiveData<String>) {
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 앱 처음 실행시 realTime 에서 총 지출 가져와 ViewModel로 넘겨주기
    fun getRealTimeTotalExpense(totalExpense: MutableLiveData<Int>) {
        totalExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 앱 처음 실행시 realTime 에서 고정 지출 가져와 ViewModel로 넘겨주기
    fun getRealTimeTotalRegExpense(totalRegExpense: MutableLiveData<Int>) {
        totalRegExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalRegExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 로그인시 realtime으로 개인정보 전달
    fun postPrivacy(email: String, pasword: String, name: String) {
        emailRef.setValue(email)
        passwordRef.setValue(pasword)
        nameRef.setValue(name)
    }
    // realTime 에 목표 금액 저장
    fun postGoalExpense(newValue: Int){
        goalExpenseRef.setValue(newValue)
    }

    // ExpMap, RegExpMap은 realTime에만 저장
    fun postExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?) {
        expenditureMapRef.setValue(newValue)
    }
    fun postRegExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?) {
        regExpenditureMapRef.setValue(newValue)
    }
    // 총지출, 고정지출들은 realTime, cloud 에 저장
    fun postMonthExpense(email: String, newValue: Int) {
        totalExpenseRef.setValue(newValue)
        db.collection("Users").document(email).update("MonthExpense", newValue)
    }
    // 총지출, 고정지출들은 realTime, cloud 에 저장
    fun postTotalExpense(email: String, newValue: Int) {
        totalExpenseRef.setValue(newValue)
        db.collection("Users").document(email).update("TotalExpense", newValue)
    }
    fun postTotalRegExpense(email: String, newValue: Int) {
        totalRegExpenseRef.setValue(newValue)
        db.collection("Users").document(email).update("RegTotalExpense", newValue)
    }

    suspend fun readDollarExchangeRate(): Float {
        return withContext(Dispatchers.IO){
            val docDollar = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_USDKRW").get()
            val tempDollar = docDollar.select(".no_today").text()
            tempDollar.substring(0,8).replace(",","").toFloat()
        }
    }

    suspend fun readEuroExchangeRate(): Float {
        return withContext(Dispatchers.IO){
            val docEuro = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_EURKRW").get()
            val tempEuro = docEuro.select(".no_today").text()
            tempEuro.substring(0,8).replace(",","").toFloat()
        }
    }

    suspend fun readYenExchangeRate(): Float {
        return withContext(Dispatchers.IO){
            val docYen = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_JPYKRW").get()
            val tempYen = docYen.select(".no_today").text()
            tempYen.substring(0,7).replace(",","").toFloat()
        }
    }
}