package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentFixedBinding
import com.example.bankappds.databinding.FragmentInputfixedBinding


class inputfixedFragment : Fragment() {

    var binding : FragmentInputfixedBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputfixedBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.btnFin?.setOnClickListener {
            val fp =FixedPay(binding?.edtWhere?.text.toString(), binding?.edtPay?.text.toString().toIntOrNull()?:0)
            val action = inputfixedFragmentDirections.actionInputfixedFragmentToFixedFragment(fp)
            findNavController().navigate(action)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()

    }

}