package com.example.bankappds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bankappds.databinding.FragmentExchangeRateBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class ExchangeRateFragment : Fragment() {
    private var binding : FragmentExchangeRateBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var exchangeRate : Float = 0f

        binding?.btnF5?.setOnClickListener {
            GlobalScope.launch {
                try {
                    val doc = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_USDKRW").get()

                    val temp = doc.select(".no_down").text()
                    exchangeRate = temp.substring(0,8).replace(",","").toFloat()
                }
                catch (e:Exception) {
                    e.printStackTrace()
                }
                //몇초마다 초기화하는 루틴 필요
            }

            binding?.txtOutput?.text = exchangeRate.toString()
        }


        binding?.btnExr?.setOnClickListener {
            val x = binding?.edtWon?.text.toString().toIntOrNull()?:0
            binding?.txtOutput?.text = (exchangeRate*x).toInt().toString()

        }

    }



}