package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankappds.databinding.FragmentInputBinding


class InputFragment : Fragment() {
    private var binding: FragmentInputBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputBinding.inflate(inflater)

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
            val temp = MainList(year!!, month!!, day!!, binding?.edtMoney?.text.toString().toIntOrNull()?:0,
                binding?.spinnerInputCategory?.selectedItem.toString(), binding?.edtMemo?.text.toString()
            )
            var action = InputFragmentDirections.actionInputFragmentToMainFragment(temp)

            findNavController().navigate(action)
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerSetting()
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