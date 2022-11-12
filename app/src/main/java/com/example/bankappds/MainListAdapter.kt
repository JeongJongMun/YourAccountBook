package com.example.bankappds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.MainListBinding

class MainListAdapter(private val calendarsList: ArrayList<MainList>)
    : RecyclerView.Adapter<MainListAdapter.MyViewHolder>(){

    // 인자로 받는 viewType 형태의 아이템 뷰를 위한 ViewHolder 객체를 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MainListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // 생성된 ViewHolder에 데이터를 바인딩하는 함수
    // 인자로 ViewHolder와 positon을 받아서 holder의 데이터를 변경
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = calendarsList[position]
        holder.txtYear.text = currentItem.year.toString()
        holder.txtMonth.text = currentItem.month.toString()
        holder.txtDay.text = currentItem.day.toString()
        holder.txtExpense.text = currentItem.expense.toString()
        holder.txtCategory.text = currentItem.category
    }

    // 전체 아이템 개수 리턴
    override fun getItemCount():Int = calendarsList.size

    // ViewHolder = 화면에 표시될 아이템 뷰를 저장하는 객체
    class MyViewHolder(private val binding: MainListBinding):
        RecyclerView.ViewHolder(binding.root) {
        val txtYear : TextView = binding.txtYear
        val txtMonth : TextView = binding.txtMonth
        val txtDay : TextView = binding.txtDay
        val txtExpense : TextView = binding.txtExpense
        val txtCategory : TextView = binding.txtCategory
    }
}