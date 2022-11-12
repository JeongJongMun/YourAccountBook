package com.example.bankappds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding

//TODO inputFragment 에서 입력하면 mainFragment 에 무제한으로 입력되게끔
//TODO MainList 클래스에서 깃발 세워서 한번만 저장되게

//TODO 다음주 보류
//TODO 달력이 넘어가면 메인 리스트도 같이 넘어가기 / 안되면 리스트를 바꾸는걸로,,
//TODO 프로필 - 원래는 로그인창이였다가 로그인 하면 프로필 뜨기
//TODO 통계 - 데이터 입력
//TODO 데이터 클래스 통합, 일별로 모을수 있게끔 클래스 하나 더 생성?

//TODO 디자인은 마지막에

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        //53min

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        //action bar의 옛날이름
        appBarConfiguration = AppBarConfiguration(
            //top레벨 프래그먼트의 아이디의 집합을 넘김
            //setOf(R.id.aboutFragment,R.id.examineFragment,R.id.settingsFragment),
            navController.graph,
            //drawer layout을 사용할 경우 써줘야함
            binding.drawerLayout
        )
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