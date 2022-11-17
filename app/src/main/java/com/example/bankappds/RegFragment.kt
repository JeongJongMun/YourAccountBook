package com.example.bankappds

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentRegBinding


class RegFragment : Fragment() {
    var binding : FragmentRegBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegBinding.inflate(inflater)

        return binding?.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInputData()

        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_regFragment_to_regInputFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getInputData() {
        val args : RegFragmentArgs by navArgs()
        if ( args.regExpenditure?.expense != null && args.regExpenditure?.expense != 0 ) {
            val day = args.regExpenditure?.day
            val expense = args.regExpenditure?.expense
            val category: Ecategory? = args.regExpenditure?.category
            val memo = args.regExpenditure?.memo.toString()

            binding?.recPay?.layoutManager = LinearLayoutManager(activity) //context
            binding?.recPay?.setHasFixedSize(true)
            binding?.recPay?.adapter=ExpenditureAdapter((activity as MainActivity).expenditureMap[day]!!)

            // 메인 리스트에 추가
            (activity as MainActivity).addExpenditure(Expenditure(0, 0, day!!, expense!!, category!!, memo))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }

        else if ( args.regExpenditure?.expense == 0 ) {
            Toast.makeText(requireContext(), "추가할 지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}