package com.example.bankappds.repository

import androidx.lifecycle.MutableLiveData
import com.example.bankappds.Expenditure
import com.example.bankappds.Test
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
    val userRefEmail = database.getReference("User")
    val userRefPassword = database.getReference("Password")
    val userRefName = database.getReference("Name")
    val userRefExpenditureMap = database.getReference("ExpenditureMap")
    val userRefRegExpenditureMap = database.getReference("RegExpenditureMap")
    val userRefTotalExpense = database.getReference("TotalExpense")
    val userRefTotalRegExpense = database.getReference("TotalRegExpense")

    fun getDataFromServer(exp: MutableLiveData<MutableMap<String, MutableList<Expenditure>>>) {
        db.collection("Data")
            .document("whdans4005@gmail.com")
            .get()
            .addOnSuccessListener {
                exp.value = it.data?.get("ExpenditureMap") as MutableMap<String, MutableList<Expenditure>>

            }
    }


    fun getRealTimeExpendtureMap(exp: MutableLiveData<MutableMap<String, MutableList<Expenditure>>>) {
        userRefExpenditureMap.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Test ${snapshot.value}")
                println("Test ${snapshot.value!!::class.simpleName}")
                exp.postValue(snapshot.value as MutableMap<String, MutableList<Expenditure>>)
/*                val item : Test? = snapshot.getValue(Test::class.java)
                exp.postValue(item?.ExpenditureMap)
                println(item?.ExpenditureMap)

                val temp = snapshot.value as MutableMap<String, MutableList<Expenditure>>
                exp.value = temp*/

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    // 앱 처음 실행시 realtime 에서 가져와서 ViewModel로 넘겨줌
    fun getRealTimeEmail(email: MutableLiveData<String>) {
        userRefEmail.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                email.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getRealTimeName(name: MutableLiveData<String>) {
        userRefName.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getRealTimeTotalExpense(totalExpense: MutableLiveData<Int>) {
        userRefTotalExpense.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun getRealTimeTotalRegExpense(totalRegExpense: MutableLiveData<Int>) {
        userRefTotalRegExpense.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalRegExpense.postValue(snapshot.value.toString().toIntOrNull() ?: 0)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // 로그인시 realtime으로 개인정보 전달
    fun postPrivacy(email: String, pasword: String, name: String) {
        userRefEmail.setValue(email)
        userRefPassword.setValue(pasword)
        userRefName.setValue(name)
    }

    // ExpMap, RegExpMap은 realTime에만 저장
    fun postExpenditureMap(email: String, newValue: MutableMap<String, MutableList<Expenditure>>?) {
        userRefExpenditureMap.setValue(newValue)
        db.collection("Users").document(email).update("ExpenditureMap", newValue)
    }
    fun postRegExpenditureMap(newValue: MutableMap<String, MutableList<Expenditure>>?, email: String) {
        userRefRegExpenditureMap.setValue(newValue)
    }
    // total 들은 realTime, cloud 에 저장
    fun postTotalExpense(email: String, newValue: Int) {
        userRefTotalExpense.setValue(newValue)
        db.collection("Users").document(email).update("TotalExpense", newValue)
    }
    fun postTotalRegExpense(email: String, newValue: Int) {
        userRefTotalRegExpense.setValue(newValue)
        db.collection("Users").document(email).update("RegTotalExpense", newValue)
    }
    fun testPost(email: String, newValue : Expenditure) {
        db.collection("Data").document(email).update(newValue.year.toString()+newValue.month.toString()+newValue.day.toString(), newValue)
//        ("Exp", newValue)
    }
}