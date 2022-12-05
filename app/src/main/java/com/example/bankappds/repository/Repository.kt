package com.example.bankappds.repository

import androidx.lifecycle.MutableLiveData
import com.example.bankappds.Expenditure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Repository {
    val database = Firebase.database

    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    // realtime 데이터 변수들
    val userRef = database.getReference("User")
    val passwordRef = database.getReference("Password")
    val nameRef = database.getReference("Name")
    val expenditureMapRef = database.getReference("ExpenditureMap")
    val regExpenditureMapRef = database.getReference("RegExpenditureMap")
    val totalExpenseRef = database.getReference("TotalExpense")
    val totalRegExpenseRef = database.getReference("TotalRegExpense")
    val goalExpenseRef = database.getReference("GoalExpense")

    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0$month"
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }
    fun addExpenditure(map: MutableMap<String, MutableList<Expenditure>>, expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year, expd.month, expd.day)

        if (map[dayInfo] != null) { // 기존 값 존재
            map[dayInfo]?.add(expd)
        } else { // 기존 값 존재 X
            map[dayInfo] = mutableListOf(expd)
        }
    }
    // realTime 에서 목표 금액 가져오기
    fun getRealTimeGoalExp(goal: MutableLiveData<Int>) {
        goalExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                goal.postValue(snapshot.value.toString().toInt())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    // 앱 처음 실행시 realtime 에서 가져와서 ViewModel로 넘겨줌
    fun getRealTimeExpendtureMap(exp: MutableLiveData<MutableMap<String, MutableList<Expenditure>>>) {
        expenditureMapRef.addValueEventListener(object : ValueEventListener {
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

    fun getRealTimeEmail(email: MutableLiveData<String>) {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getRealTimeName(name: MutableLiveData<String>) {
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getRealTimeTotalExpense(totalExpense: MutableLiveData<Int>) {
        totalExpenseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
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
        userRef.setValue(email)
        passwordRef.setValue(pasword)
        nameRef.setValue(name)
    }
    // realTime 에 목표 금액 저장
    fun postGoalExpense(newValue: Int){
        goalExpenseRef.setValue(newValue)
    }

    // ExpMap, RegExpMap은 realTime에만 저장
    fun postExpenditureMap(email: String, newValue: MutableMap<String, MutableList<Expenditure>>?) {
        expenditureMapRef.setValue(newValue)
        db.collection("Users").document(email).update("ExpenditureMap", newValue)
    }
    fun postRegExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?, email: String) {
        regExpenditureMapRef.setValue(newValue)
    }
    // total 들은 realTime, cloud 에 저장
    fun postTotalExpense(email: String, newValue: Int) {
        totalExpenseRef.setValue(newValue)
        db.collection("Users").document(email).update("TotalExpense", newValue)
    }
    fun postTotalRegExpense(email: String, newValue: Int) {
        totalRegExpenseRef.setValue(newValue)
        db.collection("Users").document(email).update("RegTotalExpense", newValue)
    }

}