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
import com.example.bankappds.databinding.FragmentMonthBinding
import com.example.bankappds.viewmodel.DataViewModel
import java.time.LocalDateTime

var ele = 11
class MonthFragment : Fragment() {

    var binding: FragmentMonthBinding? = null
    val viewModel: DataViewModel by activityViewModels()
    val months = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12)
    val monthsName = arrayListOf<String>("Jan","Feb","Mar","Apr","May","June","July"
        ,"Aug","Sep","Oct","Nov","Dec")
    var curMonth = 0
    var txtMonth :String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentTime = LocalDateTime.now()

        val mon = currentTime.month

        //binding?.txtListMonth?.text = currentTime.month.toString()

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        var adapter = ExpenditureAdapter(setRec())
        binding?.recyclerView?.adapter = adapter

        setMonth()
        if (curMonth > 9) txtMonth = curMonth.toString() else "0$curMonth"
        binding?.btnLeft?.setOnClickListener{
            curMonth -= 1
            setMonth()
            adapter = ExpenditureAdapter(setRec())
            binding?.recyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        binding?.btnRight?.setOnClickListener{
            curMonth += 1
            setMonth()
            adapter = ExpenditureAdapter(setRec())
            binding?.recyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    fun setMonth() {
        if (curMonth == 12) curMonth = 0
        if (curMonth == -1) curMonth = 11
        binding?.txtListMonth?.text = monthsName[curMonth]
        if (curMonth > 9) txtMonth = curMonth.toString() else "0$curMonth"
    }

    fun setRec(): MutableList<Expenditure> {

        val temp = mutableListOf<Expenditure>()
        val map = viewModel.expenditureMap.value
        if (map != null) {
            for ((K,V) in map){
                if (K.substring(4,6) == txtMonth){
                    for (expd in V) {
                        temp.add(expd)
                    }
                }
            }
        }
        return temp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}