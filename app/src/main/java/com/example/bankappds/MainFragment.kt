package com.example.bankappds

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var binding : FragmentMainBinding? = null
    var intCheck = 0
    // var mainArrayList = (activity as MainActivity).mainArrayList
    var mainArrayList : ArrayList<MainList> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intCheck ++
        println(intCheck)

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
        getSharedPreference("expense")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // 입력 데이터 sharedPreferences에 저장
    private fun putSharedPreference(key: String, value: String) {
        val shared = this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        val editor = shared.edit()
        if (value != null) editor.putString(key, value)
        editor.apply()
    }

    // 입력 데이터 sharedPreferences에서 가져오기
    private fun getSharedPreference(key: String): String  {
        val shared = this.requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE)
        var expense = shared.getString(key, "0").toString()
        (activity as MainActivity).totalExpense += expense.toIntOrNull() ?: 0
        println("Shared $expense, TotalExpense ${(activity as MainActivity).totalExpense}")
        return expense
    }

    // 입력창에서 데이터 받아오기
    @SuppressLint("NotifyDataSetChanged")
    private fun getInputData() {
        val args : MainFragmentArgs by navArgs()
        if ( args.mainListData?.expense != null && args.mainListData?.expense != 0 && (activity as MainActivity).inputFlag ) {
            val year = args.mainListData?.year
            val month = args.mainListData?.month
            val day = args.mainListData?.day
            val expense = args.mainListData?.expense
            val category = args.mainListData?.category.toString()
            val memo = args.mainListData?.memo.toString()

            putSharedPreference("expense", expense.toString())
            (activity as MainActivity).inputFlag = false

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