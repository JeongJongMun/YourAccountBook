package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankappds.databinding.FragmentLogoutBinding
import com.example.bankappds.viewmodel.DataViewModel


class LogoutFragment : Fragment() {
    private var binding: FragmentLogoutBinding? = null
    val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoutBinding.inflate(inflater)
        binding?.txtLogoutName?.text = viewModel.name.value

        binding?.btnLogout?.setOnClickListener {

            MyApplication.auth.signOut()
            MyApplication.email = null
            findNavController().navigate(R.id.action_logoutFragment_to_loginFragment)
        }
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}