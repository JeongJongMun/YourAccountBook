package com.example.bankappds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.CardviewItemBinding



class CardviewAdapter (
    var exchangeList: List<Float>
) : RecyclerView.Adapter<CardviewAdapter.Holder>() {

    override fun getItemCount(): Int {
        return exchangeList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardviewAdapter.Holder {
        val binding = CardviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardviewAdapter.Holder(binding)
    }

    override fun onBindViewHolder(holder: CardviewAdapter.Holder, position: Int) {
        val exRate = exchangeList[position]
        holder.bind(position, exRate)
    }

    class Holder(private val binding: CardviewItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, exchangeRate: Float) {
            val textWhere = when (pos) {
                0 -> "달러"
                1 -> "유로"
                else -> "엔"
            }
            binding?.txtExWhere?.text = textWhere
            binding?.txtOneWhere?.text = "1$textWhere 당 "
            binding?.txtRate?.text = exchangeRate.toString()+"원"
        }
    }


}