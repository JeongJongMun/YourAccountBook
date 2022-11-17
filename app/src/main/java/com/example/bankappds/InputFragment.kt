package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankappds.databinding.FragmentInputBinding
import com.example.bankappds.viewmodel.dataViewModel


class InputFragment : Fragment() {
    private var binding: FragmentInputBinding? = null

    val viewModel: dataViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputBinding.inflate(inflater)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetting()

        // 달력 날짜 전달 받기
        val args : InputFragmentArgs by navArgs()
        val year = args.calendarDate?.year
        val month = args.calendarDate?.month
        val day = args.calendarDate?.day

        // 입력창 달력 날짜 설정
        binding?.txtInputYear?.text = year.toString()
        binding?.txtInputMonth?.text = month.toString()
        binding?.txtInputDay?.text = day.toString()

        // 저장 버튼 클릭시 date, expense, category, memo 전달
        binding?.btnSave?.setOnClickListener {

            //TODO 지출 입력 안하고 뒤로가기 눌러도 전에 썼던 값이 계속 더해짐
            //TODO 힌트도 isEmpty()에 영향이 있나...?
            if (binding?.edtMoney?.text?.isEmpty() != true) {
                viewModel.plusExpense(binding?.edtMoney?.text.toString().toIntOrNull()?:0)
            }

            val temp = MainList(year!!, month!!, day!!, binding?.edtMoney?.text.toString().toIntOrNull()?:0,
                binding?.spinnerInputCategory?.selectedItem.toString(), binding?.edtMemo?.text.toString()
            )
            var action = InputFragmentDirections.actionInputFragmentToMainFragment(temp)
            (activity as MainActivity).inputFlag = true

            findNavController().navigate(action)
        }
        // 뒤로가기
        binding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_inputFragment_to_mainFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    // 카테고리 스피너 설정
    private fun spinnerSetting() {
        // 스피너에 들어갈 데이터
        val spinnerData = listOf("식비","카페,간식","편의점,마트,잡화","술,유흥","쇼핑","취미,여가","의료,건강,피트니스","주거,통신","기타")
        // 어댑터 생성 ( fragment 에서 사용하니 requireContext()를 써주자 )
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, spinnerData)
        // 어댑터 설정
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // 스피너에 어댑터 적용
        binding?.spinnerInputCategory?.adapter = adapter
    }

}