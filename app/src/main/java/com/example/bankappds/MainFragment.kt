package com.example.bankappds

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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


        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding?.btnAdd2?.isVisible = true
                // 메인 리스트 리사이클러뷰
            val layoutManager = LinearLayoutManager(context)
            binding?.recyclerView?.layoutManager = layoutManager
            binding?.recyclerView?.setHasFixedSize(true)
            binding?.recyclerView?.adapter = ExpenditureAdapter((activity as MainActivity).expenditureMap[dayOfMonth])


            binding?.btnAdd2?.setOnClickListener {
                val caldata = intArrayOf(year,month+1,dayOfMonth)
                //날짜 전달은 safe args를 이용하여 전달함
                val send = com.example.bankappds.MainFragmentDirections.actionMainFragmentToMainInputFragment(caldata) // 전달
                findNavController().navigate(send)
            }
        }
        // get Input 이거 갈아야함 -> 다른 화면에서 메인화면으로가면 자동으로 args가 있는걸로 인식해서 입렸했던 곳에 리스트 자동 추가됨 - 수정 필요 일부러 에러로 남겨둠
        //getInputData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    /*
    // 입력창에서 데이터 받아오기
    @SuppressLint("NotifyDataSetChanged")
    private fun getInputData() {
        val args : MainFragmentArgs by navArgs()
        if ( args.expenditureData != null ){ //.expense != null && args.expenditureData?.expense != 0 ) {
            val year = args.expenditureData?.year
            val month = args.expenditureData?.month
            val day = args.expenditureData?.day
            val expense = args.expenditureData?.expense
            val typeT = args.expenditureData?.category
            val memo = args.expenditureData?.memo.toString()


            // 메인 리스트에 추가
            (activity as MainActivity).addExpenditure(Expenditure(year!!, month!!, day!!, expense!!, typeT, memo))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recyclerView?.adapter?.notifyDataSetChanged()
        }
    }
    */


}