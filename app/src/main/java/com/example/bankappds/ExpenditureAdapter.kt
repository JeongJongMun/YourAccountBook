package com.example.bankappds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.ExpenditureListBinding


class ExpenditureAdapter (var pays: ArrayList<Expenditure>): RecyclerView.Adapter<ExpenditureAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ExpenditureListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(pays[position])
    }

    override fun getItemCount() = pays.size

    class Holder(private val binding: ExpenditureListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind( pay : Expenditure) {
            if ( pay.year == 0 && pay.month == 0 ) {
                binding.imgCategory.isVisible = true

                binding.imgCategory.setImageResource(
                    when(pay.category) {
                        Ecategory.FOOD -> R.drawable.food
                        Ecategory.FINANCE -> R.drawable.food
                        Ecategory.SHOPPING -> R.drawable.food
                        Ecategory.ENTERTAINMENT -> R.drawable.food
                        Ecategory.HOBBY -> R.drawable.food
                        Ecategory.HEALTH -> R.drawable.food
                        Ecategory.HOME -> R.drawable.food
                        Ecategory.ETC -> R.drawable.food
                        else -> R.drawable.food
                    }
                )
            }
            else {
                binding.imgCategory.isVisible = false
            }
            binding.txtYear.text = pay.year.toString()
            binding.txtMonth.text = pay.month.toString()
            binding.txtDay.text = pay.day.toString()
            binding.txtExpense.text = pay.expense.toString()
            binding.txtMemo.text = pay.memo
            binding.txtCategory.text = pay.category.toString()
    }
}}
