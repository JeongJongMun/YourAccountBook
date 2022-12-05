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
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentRegInputBinding
import com.example.bankappds.viewmodel.DataViewModel


class RegInputFragment : Fragment() {
    var binding : FragmentRegInputBinding ?= null
    var categoryType : Ecategory? = null
    val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegInputBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //spinner 리스트를 만듦
        val spnLst = resources.getStringArray(R.array.category_type)
        //ArrayAdapter의 두 번쨰 인자는 스피너 목록에 아이템을 그려줄 레이아웃을 지정하여 줍니다.
        //스피너를 만들기 위한 어댑터 설정
        val adapter = activity?.let {
            ArrayAdapter(it, android.R.layout.simple_spinner_item, spnLst)
        }

        binding?.spinner?.adapter = adapter

        //스피너에서 선택한 카테고리에 따라 categoryType 변수에 값을 할당함
        binding?.spinner?.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //position을 통해 스피너에서 어떤것을 선택했는지 확인함
                categoryType = when(spnLst[position]){
                    "분류" -> null
                    "식비" ->Ecategory.FOOD
                    "금융" ->Ecategory.FINANCE
                    "쇼핑" ->Ecategory.SHOPPING
                    "여가" ->Ecategory.ENTERTAIN
                    "취미" ->Ecategory.HOBBY
                    "건강" ->Ecategory.HEALTH
                    "주거" ->Ecategory.HOME
                    "기타" ->Ecategory.ETC
                    else -> null
                }
            }
            //아무것도 선택되지 않았을 때
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding?.btnRegSave?.setOnClickListener {
            //누락된 부분이 있을 경우 -> 값이 저장되지 않음
            if (categoryType == null || binding?.edtDay?.text.toString().isEmpty()
                || binding?.edtDay?.text.toString().isEmpty() || binding?.edtDay?.text.toString().isEmpty()){
                Toast.makeText(requireContext(), "누락된 부분이 있습니다", Toast.LENGTH_SHORT).show()
            }
            // 누락된 부분없이 값이 잘 입력 되었을 경우
            else {
                val temp = Expenditure(0, 0, binding?.edtDay?.text.toString().toIntOrNull()?:0,
                    binding?.edtPay?.text.toString().toIntOrNull()?:0,
                    categoryType!!, binding?.edtMemoReg?.text.toString()
                )
                //뷰모델을 통해 데이터 저장 & 네비게이션 이동
                viewModel.addRegExpenditure(temp)
                findNavController().navigate(R.id.action_regInputFragment_to_regFragment)
            }
        }
        //취소 버튼 누를시
        binding?.btnRegCancle?.setOnClickListener {
            findNavController().navigate(R.id.action_regInputFragment_to_regFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}