package com.example.bankappds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentRegBinding
import com.example.bankappds.viewmodel.DataViewModel

//고정지출 프래그먼트
class RegFragment : Fragment() {
    var binding : FragmentRegBinding?= null
    val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //고정지출이 담긴 리사이클러뷰를 생성하기 위한 코드
        binding?.recRegPays?.layoutManager = LinearLayoutManager(activity) //context
        binding?.recRegPays?.setHasFixedSize(true)
        //리사이클러뷰 어댑터에 전달하기 위한 리스트 생성
        //고정지출에 해당하는 데이터만 위의 리스트에 저장
        val adpatList = mutableListOf<Expenditure>()
        val temp = viewModel.regExpdMap.value?.toMap()
        if (temp != null){
            for ((K,V) in temp){
                for (expd in V) {
                    adpatList.add(expd)
                }
            }
        }
        //만든 리스트를 리사이클러뷰 어댑터에 전달
        val adapter = ExpenditureAdapter(adpatList)
        binding?.recRegPays?.adapter = adapter

        //고정지출 추가 버튼 누를 시 고정지출 입력창으로 이동
        binding?.btnRegAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_regFragment_to_regInputFragment)
        }

        //뷰모델의 총 고정지출을 관찰하며 바뀔시 총 고정지출 text를 변경해줌
        viewModel.totalRegExpense.observe(viewLifecycleOwner) {
            binding?.totalReg?.text = viewModel.totalRegExpense.value?.toString() // 총 고정 지출 설정
        }

        // 리사이클러뷰 객체 선택시 포지션 전달 받을 변수
        var selectedReg = -1

        // 삭제할 리스트 클릭 후 삭제 버튼 클릭시 해당 고정지출 삭제
        binding?.btnRegDelete?.setOnClickListener {
            if (selectedReg != -1) {
                viewModel.deleteRegExpenditure(adpatList[selectedReg]) // map에서 리스트 삭제
                adpatList.removeAt(selectedReg)
                adapter.notifyDataSetChanged()
                selectedReg = -1
            } else Toast.makeText(requireContext(), "삭제 할 고정지출이 없습니다.", Toast.LENGTH_SHORT).show()
        }

        // 리사이클러뷰 객체 선택시 포지션 전달
        adapter.setItemClickListener(object : ExpenditureAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                selectedReg = position
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}