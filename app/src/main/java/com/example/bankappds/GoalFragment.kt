package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentGoalBinding
import com.example.bankappds.databinding.FragmentMainInputBinding
import com.example.bankappds.viewmodel.dataViewModel

class GoalFragment : Fragment() {
    private var binding: FragmentGoalBinding    ? = null
    val viewModel: dataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnSave?.setOnClickListener {
            if (binding?.edtGoalExpd?.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "누락된 칸이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.setGoal(binding?.edtGoalExpd?.text.toString().toInt())
                Toast.makeText(requireContext(), "목표 지출 금액 : ${viewModel.goalExpense.value}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_goalFragment_to_mainFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}