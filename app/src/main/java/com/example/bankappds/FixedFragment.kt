package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentFixedBinding


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

        println(MainActivity().forcheck)
        if (MainActivity().forcheck == true) {
            payLists.add(args.inputFix!!)
            MainActivity().forcheck=false
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }
        else {
            println("DDDDDD")
            Toast.makeText(requireContext(), "XXXX", Toast.LENGTH_SHORT).show()
        }

        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnKkk?.setOnClickListener {
            payLists.add(FixedPay(PayType.MONEY,"gg",123))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }

        binding?.btnAdd?.setOnClickListener {
            MainActivity().forcheck=true
            println(MainActivity().forcheck)
            findNavController().navigate(R.id.action_fixedFragment_to_inputfixedFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}