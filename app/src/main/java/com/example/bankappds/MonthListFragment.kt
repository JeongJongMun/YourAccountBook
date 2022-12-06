package com.example.bankappds


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentMonthListBinding
import com.example.bankappds.viewmodel.DataViewModel
import java.util.*

//해당 월별 지출을 확인하기 위한 프래그먼트
class MonthListFragment : Fragment() {
    var binding: FragmentMonthListBinding? = null
    val viewModel: DataViewModel by activityViewModels()

    //캘린더 클래스를 통해 해당 월이 몇월인지 읽어옴
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthListBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //현재 해당하는 달을 읽어 옴
        val month = calendar.get(Calendar.MONTH)+1
        val year = calendar.get(Calendar.YEAR)

        println(year)
        //현재 몇월인지 출력하는 텍스트
        binding?.txtMonListTitle?.text = "${month}월 지출 내역"

        //mon월에 해당하는 데이터만 가져오기 위해 리스트를 만들고 이를 날짜 순으로 정렬하여 리사이클러뷰에 추가함
        var sortedMonthList = mutableListOf<Expenditure>()
        for (expd in viewModel.getMonthList(year,month)){
            for (i in 1 .. 31){
                if (expd.day == i) {
                    sortedMonthList.add(expd)
                }
            }
        }

        //해당 월 총 지출
        binding?.txtTotalMonthExpense?.text = viewModel.getMonthExpense(year,month).toString()

        //리사이클러 뷰 어댑터에 전달
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = ExpenditureAdapter(sortedMonthList)

    }
}