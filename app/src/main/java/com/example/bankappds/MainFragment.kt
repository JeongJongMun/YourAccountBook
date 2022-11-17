package com.example.bankappds

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMainBinding
import com.example.bankappds.viewmodel.dataViewModel

class MainFragment : Fragment() {
    private var binding : FragmentMainBinding? = null

    // 뷰모델을 어떻게 초기화할지를 viewModels라는 함수에 위임(by)한다
    // val viewModel: MbtiViewModel by viewModels()
    // 우리는 엑티비티에 프래그먼트들이 물려있으니까 엑티비티뷰모델 사용
    val viewModel: dataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰모델로 메인엑티비티에 있는 totalExpense에 더해주기
        viewModel.expense.observe(viewLifecycleOwner) {
            (activity as MainActivity).totalExpense += viewModel.expense.value!!
            dataViewModel().setZero()
            println("TotalExpense : ${(activity as MainActivity).totalExpense}")
        }


        // 메인 리스트 리사이클러뷰
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        //binding?.recyclerView?.adapter = MainListAdapter(mainArrayList)
        binding?.recyclerView?.adapter = MainListAdapter((activity as MainActivity).mainArrayList)

        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding?.btnAdd2?.isVisible = true

            binding?.btnAdd2?.setOnClickListener {
                val temp = com.example.bankappds.MainList(year, month + 1, dayOfMonth, 0, "", "")
                val send = com.example.bankappds.MainFragmentDirections.actionMainFragmentToInputFragment(temp) // 전달
                findNavController().navigate(send)

            }




        }

        getInputData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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

            (activity as MainActivity).inputFlag = false

            // 메인 리스트에 추가
            //mainArrayList.add(MainList(year!!, month!!, day!!, expense!!, category, memo))
            (activity as MainActivity).mainArrayList.add(MainList(year!!, month!!, day!!, expense!!, category, memo))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recyclerView?.adapter?.notifyDataSetChanged()
        }

        else if ( args.mainListData?.expense == 0 ) {
            Toast.makeText(requireContext(), "추가할 지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}