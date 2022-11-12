package com.example.bankappds

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var binding : FragmentMainBinding? = null
    private var mainArrayList : ArrayList<MainList> = arrayListOf()


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
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = MainListAdapter(mainArrayList)

        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val temp = MainList(year, month+1, dayOfMonth, 0, "", "")
            val send = MainFragmentDirections.actionMainFragmentToInputFragment(temp) // 전달
            findNavController().navigate(send)
        }
        binding?.imageView2?.setOnClickListener {
            mainArrayList.add(MainList(2000, 10, 17, 10000, "테스트", "성공"))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recyclerView?.adapter?.notifyDataSetChanged()
        }
        getInputData()
    }

    // 입력창에서 데이터 받아오기
    @SuppressLint("NotifyDataSetChanged")
    private fun getInputData() {
        println("onResume")
        val args : MainFragmentArgs by navArgs()
        if ( args.mainListData?.expense != null && args.mainListData?.expense != 0 ) {
            val year = args.mainListData?.year
            val month = args.mainListData?.month
            val day = args.mainListData?.day
            val expense = args.mainListData?.expense
            val category = args.mainListData?.category.toString()
            val memo = args.mainListData?.memo.toString()

            // 메인 리스트에 추가
            mainArrayList.add(MainList(year!!, month!!, day!!, expense!!, category, memo))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recyclerView?.adapter?.notifyDataSetChanged()
        }

        else if ( args.mainListData?.expense == 0 ) {
            Toast.makeText(requireContext(), "추가할 지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}