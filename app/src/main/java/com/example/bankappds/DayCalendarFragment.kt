package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentDayCalendarBinding
import com.example.bankappds.viewmodel.dataViewModel

class DayCalendarFragment : Fragment() {
    var binding: FragmentDayCalendarBinding? = null
    val viewModel: dataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDayCalendarBinding.inflate(inflater,container, false)
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
            binding?.btnAdd?.isVisible = true
            // 메인 리스트 리사이클러뷰
            val layoutManager = LinearLayoutManager(context)
            binding?.recyclerView?.layoutManager = layoutManager
            binding?.recyclerView?.setHasFixedSize(true)

            val todayList = expenditureMap[makeDayStr(year,month+1,dayOfMonth)]
            val regList = regExpdMap["000000${dayOfMonth}"]

            val totalList: List<Expenditure> = todayList.orEmpty() + regList.orEmpty()

            binding?.recyclerView?.adapter = ExpenditureAdapter(totalList.toMutableList())



            binding?.btnAdd?.setOnClickListener {
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


}