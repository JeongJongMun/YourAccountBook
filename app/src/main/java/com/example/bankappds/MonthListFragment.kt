package com.example.bankappds

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMonthListBinding
import com.example.bankappds.viewmodel.DataViewModel
import java.time.LocalDateTime
import java.util.*


class MonthListFragment : Fragment() {
    var binding: FragmentMonthListBinding? = null
    val viewModel: DataViewModel by activityViewModels()
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentTime = LocalDateTime.now()

        val mon = calendar.get(Calendar.MONTH)+1

        binding?.txtNowmon?.text = "${mon}월 지출 내역"
        var sortedMonthList = mutableListOf<Expenditure>()
        for (expd in viewModel.getMonthList(mon)){
            for (i in 1 .. 31){
                if (expd.day == i) {
                    sortedMonthList.add(expd)
                }
            }
        }

        binding?.txtTotalMonthExpense?.text = viewModel.getMonthExpense(mon).toString()


        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = ExpenditureAdapter(sortedMonthList)

    }
}