package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentFixedBinding


class FixedFragment : Fragment() {
    var binding : FragmentFixedBinding ?= null

    var payLists = arrayOf(
        FixedPay(PayType.ENTE,"Netflix",12000,true),
        FixedPay(PayType.HOME,"전기세",30000,true),
        FixedPay(PayType.FOOD,"급식비",300000,true)
    )



    val args: FixedFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFixedBinding.inflate(inflater)
        binding?.recPay?.layoutManager = LinearLayoutManager(activity)

        /*
        if (args.inputFix.go){
            payLists = payLists.plus(FixedPay(PayType.HOME,args.inputFix.where,args.inputFix.money,true))
        }
        */
        payLists = payLists.plus(FixedPay(PayType.HOME,"haha",12333,true))

        binding?.recPay?.adapter=FixedPayAdapter(payLists)
        return binding?.root


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.txtMon?.text = args.inputFix?.money.toString()


        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_fixedFragment_to_inputfixedFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}