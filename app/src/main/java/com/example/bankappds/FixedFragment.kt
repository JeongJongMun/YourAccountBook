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


    var forcheck = 0

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

        if (forcheck == 1) {
            payLists.add(args.inputFix!!)
            forcheck=0
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

        binding?.btnAdd?.setOnClickListener {
            forcheck=1
            println(forcheck)
            findNavController().navigate(R.id.action_fixedFragment_to_inputfixedFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}