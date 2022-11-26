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
    lateinit var binding2: FragmentMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = FragmentMainBinding.inflate(layoutInflater)

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")
        binding2?.btnAlarm?.setOnClickListener {
            displayNotification()
        }


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
    val CHANNEL_ID : String = "MY_CH"
    private var notificationManager: NotificationManager? = null

    private fun displayNotification() {
        val notificationId = 45

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example")
            .setContentText("This is Notification Test")
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }


/*    val channel_name : String = "CHANNEL_1"
    val notificationId: Int = 1002
    private fun createNotificationChannel(builder: NotificationCompat.Builder,
                                          notificationId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "1번 채널입니다."
            val importance = NotificationManager.IMPORTANCE_DEFAULT // 중요도 설정
            // 채널 생성
            val channel = NotificationChannel(CHANNEL_ID, channel_name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            notificationManager.notify(notificationId, builder.build())
        }
    }

    private fun displayNotification() {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Default notification")
            .setContentText("Notification Test")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // 알랍 탭하면 삭제

        createNotificationChannel(builder, notificationId)
    }*/

}