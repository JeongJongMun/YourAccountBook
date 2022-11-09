package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankappds.databinding.FragmentFixedBinding




class FixedFragment : Fragment() {
    var binding : FragmentFixedBinding ?= null

    val args: FixedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFixedBinding.inflate(inflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.txtWh?.text = args.inputFix?.where
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