package com.example.bankappds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding
import java.lang.reflect.Executable

//TODO 조건문 가라 금지

//TODO 한달 통계 데이터 관리
//TODO 프로필 - 원래는 로그인창이였다가 로그인 하면 프로필 뜨기
//TODO 앱바 - 오른쪽에 버튼 누르면 한달 통계 리사이클러뷰
//TODO 통계 - 데이터 입력

//TODO 각 프래그먼트 xml 정리

//TODO 디자인은 마지막에

//TODO 앱 컨셉을 저축왕 느낌으로 게이지 채우는 형식

/*
var testObject = Expenditure(0, 0,10,0,Ecategory.FOOD,"") // 객체 생성

var map = mutableMapOf<Int, MutableList<Expenditure>>()
if (map[testObject.day] != null) {
    map[testObject.day]?.add(testObject)
}
else map.put(testObject.day, mutableListOf<Expenditure>(testObject))
println(map[10])
*/



class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration


    var expenditureMap = mutableMapOf<String, MutableList<Expenditure>>()
//    99 to mutableListOf
//    (Expenditure(0,0,0,0,Ecategory.ETC,"")))
    var totalExpense = 0

    //TODO sharedPreferences로 내부에 영구 저장 되게 하기
    //TODO put은 MainFragment에서 사용, value를 arraylist 넘기는 법 공부

/*    // 입력 데이터 sharedPreferences에서 가져오기
    private fun getSharedPreference(key: String): String  {
        val shared = this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        var expense = shared.getString(key, "0").toString()
        (activity as MainActivity).totalExpense += expense.toIntOrNull() ?: 0
        println("Shared $expense, TotalExpense ${(activity as MainActivity).totalExpense}")
        return expense
    }
    getSharedPreference("expense")

    // 입력 데이터 sharedPreferences에 저장
    private fun putSharedPreference(key: String, value: String) {
        val shared = this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = shared.edit()
        if (value != null) editor.putString(key, value)
        editor.apply()
    }
    putSharedPreference("expense", expense.toString())*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        //action bar의 옛날이름
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout) //drawer layout을 사용할 경우 써줘야함
        //nav controll 할 때 top level에서는 표시하지 않게
        setupActionBarWithNavController(navController,appBarConfiguration) //네비게이션과 연결시킴
        binding.drawerNav.setupWithNavController(navController)
        setContentView(binding.root)
    }

    //up버튼에 대한 반응 세팅 - default-기본은 back 동작을 안함
    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun addExpenditure(expd: Expenditure) {
        val dayInfo = makeDayStr(expd.year,expd.month,expd.day)

        if (expenditureMap[dayInfo] != null) {
            expenditureMap[dayInfo]?.add(expd)
        } else {
            expenditureMap.put(dayInfo, mutableListOf(expd))
        }
    }

    fun makeDayStr(year: Int, month: Int, day: Int): String {
        val yearStr = if (year == 0) "0000" else year.toString()
        val monthStr = if (month > 9) month.toString() else "0"+month.toString()
        val dayStr = day.toString()

        return yearStr+monthStr+dayStr
    }


}