package com.example.bankappds

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.FixedListBinding


class FixedPayAdapter (val pays: Array<FixedPay>): RecyclerView.Adapter<FixedPayAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FixedListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(pays[position])
    }

    override fun getItemCount() = pays.size

    class Holder(private val binding: FixedListBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind( pay : FixedPay) {
            binding.imageView.setImageResource( when(pay.type) {
                PayType.ENTE -> R.drawable.ent
                PayType.FOOD -> R.drawable.food
                PayType.MONEY -> R.drawable.money
                PayType.HOME -> R.drawable.house
            })
            binding.lstWhere.text = pay.where
            binding.lstMon.text = pay.money.toString()


    }
}}
