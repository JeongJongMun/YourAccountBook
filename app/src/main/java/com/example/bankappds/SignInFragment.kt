package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bankappds.MyApplication.Companion.auth
import com.example.bankappds.databinding.FragmentSignInBinding
import com.example.bankappds.viewmodel.DataViewModel
import com.google.firebase.firestore.FirebaseFirestore

class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null
    val viewModel: DataViewModel by activityViewModels()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance() // 인스턴스 생성

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater)

        binding?.btnSignIn?.setOnClickListener {
            val email = binding?.edtEmail?.text.toString().trim()
            val password = binding?.edtPassword?.text.toString().trim()
            val name = binding?.edtName?.text.toString().trim()
            createUser(email, password, name)

        }

        return binding?.root
    }


    private fun createUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { sendTask ->
                    if (sendTask.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "회원 가입에 성공했습니다. 전송된 메일을 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val user = hashMapOf(
                            "Name" to name,
                            "TotalExpense" to 0,
                            "RegTotalExpense" to 0
                        )

                        db.collection("Users").document(email).set(user)
                        findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "메일 전송에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
                    }
                }
            }
            else {
                Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
                Toast.makeText(requireContext(), "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}