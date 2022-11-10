package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankappds.databinding.FragmentInputBinding


class InputFragment : Fragment() {
    private var binding: FragmentInputBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputBinding.inflate(inflater, container, false)
        // 달력 날짜 전달 받기
        val args : InputFragmentArgs by navArgs()
        val year = args.calendarDate?.year
        val month = args.calendarDate?.month
        val day = args.calendarDate?.day

        // 입력창 달력 날짜 설정
        binding?.txtInputYear?.text = year.toString()
        binding?.txtInputMonth?.text = month.toString()
        binding?.txtInputDay?.text = day.toString()

        // 저장 버튼 클릭시 날짜, 금액 전달
        binding?.btnSave?.setOnClickListener {
            val temp = MainList(year!!, month!!, day!!, binding?.edtMoney?.text.toString().toIntOrNull()?:0)
            var action = InputFragmentDirections.actionInputFragmentToMainFragment(temp)

            findNavController().navigate(action)
        }

        return binding!!.root
    }

}