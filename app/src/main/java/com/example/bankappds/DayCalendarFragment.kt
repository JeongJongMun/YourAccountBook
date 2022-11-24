package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            println("TotalExpense : ${viewModel.expense}")
        }
        viewModel.expenditureMap.observe(viewLifecycleOwner) {
            println("ExpenditureMap : $it")
        }
        viewModel.regExpdMap.observe(viewLifecycleOwner) {
            println("RegExpenditureMap : $it")
        }

        // 달력 날짜 선택시 날짜 전달, 이동
        binding?.calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding?.btnDelete?.isVisible = true
            binding?.btnAdd?.isVisible = true
            // 메인 리스트 리사이클러뷰
            val layoutManager = LinearLayoutManager(context)
            binding?.recyclerView?.layoutManager = layoutManager
            binding?.recyclerView?.setHasFixedSize(true)


            val todayList = viewModel.expenditureMap.value?.toMutableMap()?.get(viewModel.makeDayStr(year,month+1,dayOfMonth))
            val regList = viewModel.regExpdMap.value?.toMutableMap()?.get("000000${dayOfMonth}")

            val totalList: MutableList<Expenditure> = (todayList.orEmpty() + regList.orEmpty()).toMutableList()

            val adapter = ExpenditureAdapter(totalList)
            binding?.recyclerView?.adapter = adapter

            binding?.btnAdd?.setOnClickListener {
                val caldata = intArrayOf(year,month+1,dayOfMonth)
                //날짜 전달은 safe args를 이용하여 전달함
                val send = com.example.bankappds.MainFragmentDirections.actionMainFragmentToMainInputFragment(caldata) // 전달
                findNavController().navigate(send)
            }

            // 리사이클러뷰 객체 선택시 포지션 전달 받을 변수
            var selectedReg = -1
            binding?.btnDelete?.setOnClickListener {
                if (selectedReg != -1 && todayList != null) {
                    viewModel.deleteExpenditure(todayList[selectedReg]) // map에서 리스트 삭제
                    //adapter.notifyItemRemoved(selectedReg) // 삭제되었음을 알림
                    adapter.notifyDataSetChanged()
                    selectedReg = -1
                } else Toast.makeText(requireContext(), "삭제 할 일일 지출이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            // 리사이클러뷰 객체 선택시 포지션 전달
            adapter.setItemClickListener(object : ExpenditureAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    selectedReg = position
                    println("$selectedReg 번 선택")
                }
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}