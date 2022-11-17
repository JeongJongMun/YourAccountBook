package com.example.bankappds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.FixedListBinding


class FixedPayAdapter (var pays: ArrayList<FixedPay>): RecyclerView.Adapter<FixedPayAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FixedListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(pays[position])
    }

    override fun getItemCount() = pays.size

    /*
    fun addItem(addPay:FixedPay){
        pays.plus(addPay)
        notifyDataSetChanged()
    }
     */

    class Holder(private val binding: FixedListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind( pay : FixedPay) {
            binding.imageView.setImageResource( when(pay.type) {
                Ecategory.ENTERTAINMENT -> R.drawable.ent
                Ecategory.FOOD -> R.drawable.food
                Ecategory.FINANCE -> R.drawable.money
                Ecategory.HOME -> R.drawable.house
                else -> 0
            })
            binding.lstWhere.text = pay.where
            binding.lstMon.text = pay.money.toString()


    }
}}
