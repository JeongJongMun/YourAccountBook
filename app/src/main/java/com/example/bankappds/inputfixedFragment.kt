package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentFixedBinding
import com.example.bankappds.databinding.FragmentInputfixedBinding



class inputfixedFragment : Fragment() {

    var binding : FragmentInputfixedBinding ?= null


    var typeT : PayType? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputfixedBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var spnLst = resources.getStringArray(R.array.fixed_type)
        //ArrayAdapter의 두 번쨰 인자는 스피너 목록에 아이템을 그려줄 레이아웃을 지정하여 줍니다.
        val adapter = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                spnLst
            )
        }



        //activity_main에서 만들어 놓은 spinner에 adapter 연결하여 줍니다.
        binding?.spinner?.adapter = adapter
        //데이터가 들어가 있는 spinner 에서 선택한 아이템을 가져옵니다.
        binding?.spinner?.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //position은 선택한 아이템의 위치를 넘겨주는 인자입니다.
                typeT = when(spnLst.get(position)){
                    "분류" -> null
                    "문화" ->PayType.ENTE
                    "가정" ->PayType.HOME
                    "금융" ->PayType.MONEY
                    "식비" ->PayType.FOOD
                    else -> null
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding?.btnFin?.setOnClickListener {
            if (typeT == null ){
                Toast.makeText(requireContext(), "누락된 부분이 있다", Toast.LENGTH_SHORT).show()
            }
            else {
                MainActivity().forcheck = true
                print(MainActivity().forcheck)
                val fp =FixedPay(typeT!!,binding?.edtWhere?.text.toString(), binding?.edtPay?.text.toString().toIntOrNull()?:0)
                val action = inputfixedFragmentDirections.actionInputfixedFragmentToFixedFragment(fp)
                findNavController().navigate(action)
            }
        }

        binding?.btnCancle?.setOnClickListener {
            findNavController().navigate(R.id.action_inputfixedFragment_to_fixedFragment)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }


}