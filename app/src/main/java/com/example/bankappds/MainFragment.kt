package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    var binding : FragmentMainBinding? = null
    private lateinit var mainArrayList : Array<MainList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메인 리스트 리사이클러뷰
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = MainListAdapter(mainArrayList)

        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val temp = MainList(year, month+1, dayOfMonth, 0)
            val send = MainFragmentDirections.actionMainFragmentToInputFragment(temp) // 전달
            findNavController().navigate(send)
        }
/*        val argsCheck : MainFragmentArgs by navArgs()
        if (argsCheck == null)*/

    }

    // 입력 창에서 데이터 받아오는 함수
    fun dataReceive() {
        // 입력창에서 데이터 받아옴
        val args : MainFragmentArgs by navArgs()
        val year = args.mainListData?.year
        val month = args.mainListData?.month
        val day = args.mainListData?.day
        val expense = args.mainListData?.expense
        // 메인 리스트에 추가
        mainArrayList = mainArrayList.plus(MainList(year!!, month!!, day!!, expense!!))
    }

    private fun dataInitialize() {
        mainArrayList = arrayOf(
        MainList(2022, 11,1,10000)
        )
    }
}