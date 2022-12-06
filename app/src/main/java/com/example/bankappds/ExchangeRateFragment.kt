package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.bankappds.databinding.FragmentExchangeRateBinding
import com.example.bankappds.viewmodel.DataViewModel


class ExchangeRateFragment : Fragment() {
    private var binding: FragmentExchangeRateBinding? = null
    val viewModel: DataViewModel by activityViewModels()

    var exchangeWhere : Float? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetting()
        //뷰모델에 있는 코루틴을 사용하여 Jsoup을 통해 환율값을 받아오는 함수 사용
        viewModel.retrieveExchangeDollarRate()

        //3개의 환율을 저장하는 리스트
        val exchangeRateList = mutableListOf<Float>()
        exchangeRateList.add(viewModel.exchangeDollarRate.value?:0f)
        exchangeRateList.add(viewModel.exchangeEuroRate.value?:0f)
        exchangeRateList.add(viewModel.exchangeYenRate.value?:0f)

        //카드뷰 어댑터에 환율데이터 리스트를 넘김
        binding?.viewPager?.adapter = CardviewAdapter(exchangeRateList)

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

        //환율 계산하는 함수
        binding?.btnCalculate?.setOnClickListener {
            if (binding?.edtWonTo?.text != null && exchangeWhere != null ) {
                val tempWon = binding?.edtWonTo?.text.toString().toFloat()
                val tempExRate = exchangeWhere?:0f
                binding?.txtAfterCalc?.text = (tempWon * tempExRate).toInt().toString()+"원"
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
                    "달러" -> viewModel.exchangeDollarRate.value
                    "유로" -> viewModel.exchangeDollarRate.value
                    "엔" -> viewModel.exchangeDollarRate.value
                    else -> null
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

}





