package com.example.bankappds

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bankappds.databinding.ActivityMainBinding

//TODO fragment 위에서 fragment 띄우기 (아니면 더 다른 좋은게 있나..) / 검색-카테고리, 메인프레그먼트-입력창

class MainActivity : AppCompatActivity() {
    //haha
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