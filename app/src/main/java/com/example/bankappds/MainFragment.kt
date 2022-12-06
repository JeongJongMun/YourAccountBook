package com.example.bankappds

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMainBinding
import com.example.bankappds.viewmodel.DataViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainFragment : Fragment() {

    // 프래그먼트에서 context 사용 가능
    lateinit var mainActivity: MainActivity
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    private var binding : FragmentMainBinding? = null
    val viewModel: DataViewModel by activityViewModels()
    val calendar: Calendar = Calendar.getInstance()
    private val channelId: String = "MY_CH"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.name.observe(viewLifecycleOwner) {
            binding?.txtName?.text = viewModel.name.value?: "Unknown"
        }
        viewModel.email.observe(viewLifecycleOwner){
            println(it)
        }

        binding?.imgBtnProfile?.setOnClickListener {
            if (MyApplication.checkAuth()) {
                findNavController().navigate(R.id.action_mainFragment_to_logoutFragment)
            }
            else findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
        createNotificationChannel(channelId, "warningChannel", "totalExpense over goalExpense")
        viewModel.totalExpense.observe(viewLifecycleOwner) { // 총 지출이 목표 지출보다 높을 경우 경고 알람
            if (viewModel.goalExpense.value?.toString() != null){
                if (it > viewModel.goalExpense.value.toString().toInt()) {
                    displayNotification()
                }
            }
        }

        val month = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)

        val goalExpense = if ((viewModel.goalExpense.value ?: 0) == 0) 1 else viewModel.goalExpense.value?:0
        val monthTotalExpense  = viewModel.getMonthExpense(year,month).toFloat()
        val percentage = if ( ((monthTotalExpense / goalExpense) * 100).toInt() >= 100 ) 100
        else ((monthTotalExpense / goalExpense) * 100).toInt()


        binding?.progressBar?.progress= percentage



        viewModel.totalExpense.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "현재 총 지출 : ${viewModel.totalExpense.value}", Toast.LENGTH_SHORT).show()
            if (viewModel.goalExpense.value?.toInt() != 0) {
                if (it > (viewModel.goalExpense.value?.toInt() ?: 0)) {
                    Toast.makeText(requireContext(), "지출이 목표 지출을 넘어섰습니다!!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "현재 총 지출 : ${viewModel.totalExpense.value}, 목표 지출 : ${viewModel.goalExpense.value}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            binding?.btnDayAdd?.isVisible = true
            binding?.btnDayDelete?.isVisible = true
            // 메인 리스트 리사이클러뷰
            val layoutManager = LinearLayoutManager(context)
            binding?.recyclerView?.layoutManager = layoutManager
            binding?.recyclerView?.setHasFixedSize(true)

            val todayList = viewModel.expenditureMap.value?.toMutableMap()?.get(viewModel.makeDayStr(year,month+1,dayOfMonth))
            val regList = viewModel.regExpdMap.value?.toMutableMap()?.get("000000${dayOfMonth}")

            val totalList: MutableList<Expenditure> = (todayList.orEmpty() + regList.orEmpty()).toMutableList()

            val adapter = ExpenditureAdapter(totalList)
            binding?.recyclerView?.adapter = adapter

            binding?.btnDayAdd?.setOnClickListener {
                val caldata = intArrayOf(year,month+1,dayOfMonth)
                //날짜 전달은 safe args를 이용하여 전달함
                val send = com.example.bankappds.MainFragmentDirections.actionMainFragmentToMainInputFragment(caldata) // 전달
                findNavController().navigate(send)
            }

            // 리사이클러뷰 객체 선택시 포지션 전달 받을 변수
            var recPos = -1
            binding?.btnDayDelete?.setOnClickListener {
                if (recPos != -1) {
                    if (totalList[recPos].year == 0) {
                        viewModel.deleteRegExpenditure(totalList[recPos])
                    }
                    else {
                        viewModel.deleteExpenditure(totalList[recPos])
                    }
                    totalList.removeAt(recPos)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "삭제 할 일일 지출이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            // 리사이클러뷰 객체 선택시 포지션 전달
            adapter.setItemClickListener(object : ExpenditureAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    recPos = position
                }
            })
        }
    }

    private var notificationManager: NotificationManager? = null

    fun displayNotification() {
        val notificationId = 45
        // 알람 클릭시 넘어갈 엑티비티 설정
        val mPendingIntent = PendingIntent.getActivity(requireContext(), 0,
            Intent(requireContext(), MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("지출 경고!!")
            .setContentText("총 지출이 목표 지출을 넘어섰습니다!! 총 지출 : ${viewModel.totalExpense.value}, 목표 지출 : ${viewModel.goalExpense.value} ")
            .setContentIntent(mPendingIntent) // PendingIntent 설정
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            notificationManager = mainActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

}