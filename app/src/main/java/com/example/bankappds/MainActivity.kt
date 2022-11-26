package com.example.bankappds

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding
import com.example.bankappds.databinding.FragmentMainBinding
import com.example.bankappds.viewmodel.dataViewModel


//TODO 파이어베이스 데이터 영구 저장, 다른 사람이 데이터 받아 올 수 있게
//TODO API 알람 등 추가 기능

//TODO 프로필 - 개인 정보 / 목표 금액 / 한달 총 지출
//TODO 통계 - 데이터 입력
//TODO 검색 - 데이터 검색

//TODO 함수 클래스 변수 이름 정리

//TODO 디자인은 마지막에

//TODO 앱 컨셉을 저축왕 느낌으로 게이지 채우는 형식

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    val viewModel: dataViewModel by viewModels()

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