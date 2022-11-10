package com.example.bankappds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter(private val calendarsList: Array<MainList>)
    : RecyclerView.Adapter<MainListAdapter.MyViewHolder>(){

    // 인자로 받는 viewType 형태의 아이템 뷰를 위한 ViewHolder 객체를 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_list,
        parent, false)
        return MyViewHolder(itemView)
    }

    // 생성된 ViewHolder에 데이터를 바인딩하는 함수
    // 인자로 ViewHolder와 positon을 받아서 holder의 데이터를 변경
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = calendarsList[position]
        holder.txtYear.text = currentItem.year.toString()
        holder.txtMonth.text = currentItem.month.toString()
        holder.txtDay.text = currentItem.day.toString()
        holder.txtSubtractMoney.text = currentItem.subtractMoney.toString()
        holder.txtMoney.text = currentItem.money.toString()
    }

    // 전체 아이템 개수 리턴
    override fun getItemCount():Int = calendarsList.size

    // ViewHolder = 화면에 표시될 아이템 뷰를 저장하는 객체
    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val txtYear : TextView = itemView.findViewById(R.id.txt_year)
        val txtMonth : TextView = itemView.findViewById(R.id.txt_month)
        val txtDay : TextView = itemView.findViewById(R.id.txt_day)
        val txtSubtractMoney : TextView = itemView.findViewById(R.id.txt_subtractMoney)
        val txtMoney : TextView = itemView.findViewById(R.id.txt_money)
    }
}