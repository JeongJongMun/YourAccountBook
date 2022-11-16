package com.example.bankappds

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentFixedBinding
import com.example.bankappds.databinding.FragmentMainBinding


class FixedFragment : Fragment() {
    var binding : FragmentFixedBinding ?= null

    var payLists : ArrayList<FixedPay> = arrayListOf(
        FixedPay(PayType.ENTE,"Netflix",12000),
        FixedPay(PayType.HOME,"전기세",30000),
        FixedPay(PayType.FOOD,"급식비",300000)
    )

    val args: FixedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFixedBinding.inflate(inflater)

        binding?.recPay?.layoutManager = LinearLayoutManager(activity) //context
        binding?.recPay?.setHasFixedSize(true)
        binding?.recPay?.adapter=FixedPayAdapter(payLists)

        //https://ddolcat.tistory.com/592

        return binding?.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnKkk?.setOnClickListener {
            payLists.add(FixedPay(PayType.MONEY,"gg",123))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }

        binding?.btnAdd?.setOnClickListener {
            (activity as MainActivity?)?.forcheck = true
            Toast.makeText(requireContext(), "${MainActivity().forcheck}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fixedFragment_to_inputfixedFragment)
        }

        // 고정지출 입력 확인
        if (MainActivity().forcheck) {
            MainActivity().forcheck = false
            payLists.add(args.inputFix!!)
            binding?.recPay?.adapter?.notifyDataSetChanged()
            //Toast.makeText(requireContext(), "${MainActivity().forcheck}", Toast.LENGTH_SHORT).show()
        }
        else {
            //Toast.makeText(requireContext(), "${MainActivity().forcheck}", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}