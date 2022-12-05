package com.example.bankappds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.bankappds.databinding.FragmentExchangeRateBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


class ExchangeRateFragment : Fragment() {
    private var binding: FragmentExchangeRateBinding? = null

    var exchangeRateDollar: Float = 0f
    var exchangeRateEuro: Float = 0f
    var exchangeRateYen: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Thread(Runnable {
            try {
                //원달러 환율
                val docDollar = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_USDKRW").get()
                val tempDollar = docDollar.select(".no_down").text()
                exchangeRateDollar = tempDollar.substring(0,8).replace(",","").toFloat()

                val docEuro = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_EURKRW").get()
                val tempEuro = docEuro.select(".no_down").text()
                exchangeRateEuro = tempEuro.substring(0,8).replace(",","").toFloat()
                print(exchangeRateEuro)

                val docYen = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_JPYKRW").get()
                val tempYen = docYen.select(".no_down").text()
                exchangeRateYen = tempYen.substring(0,7).replace(",","").toFloat()
                print(exchangeRateYen)
            }
            catch (e:Exception) {
                e.printStackTrace()
            }
            (activity as MainActivity).runOnUiThread(java.lang.Runnable {
                binding?.txtDollarRate?.text = exchangeRateDollar.toString()
                binding?.txtEuroRate?.text = exchangeRateEuro.toString()
                binding?.txtYenRate?.text = exchangeRateYen.toString()
            })
        }).start()
    }



}


