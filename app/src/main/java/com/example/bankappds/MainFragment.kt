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
    private var position = FIRST_POSITION
    companion object {
        const val FIRST_POSITION = 1
        const val SECOND_POSITION = 2
    }

    // 뷰모델을 어떻게 초기화할지를 viewModels라는 함수에 위임(by)한다
    // val viewModel: MbtiViewModel by viewModels()
    // 우리는 엑티비티에 프래그먼트들이 물려있으니까 엑티비티뷰모델 사용
    //sdasdsa
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().replace(R.id.frm_fragment, DayCalendarFragment()).commit()

        binding?.btnChange?.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            when(position) {
                FIRST_POSITION -> {
                    transaction.replace(R.id.frm_fragment, MonthFragment()).commit()
                    position = SECOND_POSITION
                }
                SECOND_POSITION -> {
                    transaction.replace(R.id.frm_fragment, DayCalendarFragment()).commit()
                    position = FIRST_POSITION
                }
            }
        }
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