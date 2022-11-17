package com.example.bankappds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding

//TODO 다음주 보류
//TODO 달력이 넘어가면 메인 리사이클러뷰도 같이 넘어가기 OR 커스텀 캘린더 OR 버튼으로 전환
//TODO 프로필 - 원래는 로그인창이였다가 로그인 하면 프로필 뜨기
//TODO 통계 - 데이터 입력
//TODO 데이터 클래스 통합, 일별로 모을수 있게끔 클래스 하나 더 생성?

//TODO 디자인은 마지막에




class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration

    var expenditureList : ArrayList<Expenditure> = arrayListOf()
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

}