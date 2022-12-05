package com.example.bankappds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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

    var exchangeWhere : Float = 0f

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
                val tempDollar = docDollar.select(".no_today").text()
                print("dollar")
                println(tempDollar)
                exchangeRateDollar = tempDollar.substring(0,8).replace(",","").toFloat()

                val docEuro = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_EURKRW").get()
                val tempEuro = docEuro.select(".no_today").text()
                exchangeRateEuro = tempEuro.substring(0,8).replace(",","").toFloat()

                val docYen = Jsoup.connect("https://finance.naver.com/marketindex/exchangeDetail.nhn?marketindexCd=FX_JPYKRW").get()
                val tempYen = docYen.select(".no_today").text()
                exchangeRateYen = tempYen.substring(0,7).replace(",","").toFloat()

                val ratesList = listOf(exchangeRateDollar,exchangeRateEuro,exchangeRateYen)
                val adapter = CardviewAdapter(ratesList)
                binding?.viewPager?.adapter = CardviewAdapter(ratesList)
            }
            catch (e:Exception) {
                e.printStackTrace()
            }
        }).start()

        //스피너 세팅
        spinnerSetting()
//
//        val ratesList = listOf(exchangeRateDollar,exchangeRateEuro,exchangeRateYen)
//        val adapter = CardviewAdapter(ratesList)
//        binding?.viewPager?.adapter = CardviewAdapter(ratesList)

        binding?.viewPager?.setPadding(30, 0, 30, 0)
        binding?.viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding?.btnCalculate?.setOnClickListener {
            if (binding?.edtWonTo?.text != null && exchangeWhere != 0f ) {
                val tempWon = binding?.edtWonTo?.text.toString().toFloat()
                val tempExRate = exchangeWhere
                binding?.txtAfterCalc?.text = (tempWon * tempExRate).toString()
            } else {
                Toast.makeText(requireContext(), "누락된 부분이 있습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun spinnerSetting() {
        val spnLst = resources.getStringArray(R.array.exchange_where)
        //ArrayAdapter의 두 번쨰 인자는 스피너 목록에 아이템을 그려줄 레이아웃을 지정하여 줍니다.
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, spnLst)
        //activity_main에서 만들어 놓은 spinner에 adapter 연결하여 줍니다.
        binding?.exchangeSpinner?.adapter = adapter
        //데이터가 들어가 있는 spinner 에서 선택한 아이템을 가져옵니다.
        binding?.exchangeSpinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //position은 선택한 아이템의 위치를 넘겨주는 인자입니다.
                exchangeWhere = when(spnLst[position]){
                    "분류" -> 0f
                    "달러" -> exchangeRateDollar
                    "유로" -> exchangeRateEuro
                    "엔" -> exchangeRateYen
                    else -> 0f
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

}





