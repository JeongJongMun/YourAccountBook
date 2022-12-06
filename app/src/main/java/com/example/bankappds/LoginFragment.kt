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
import com.example.bankappds.databinding.FragmentLoginBinding
import com.example.bankappds.repository.Repository
import com.example.bankappds.viewmodel.DataViewModel
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    val viewModel: DataViewModel by activityViewModels()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance() // 인스턴스 생성


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding?.btnLoginSignIn?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }

        // 로그인 과정
        binding?.btnLogin?.setOnClickListener {
            if (binding?.edtEmail?.text?.isEmpty() == false && binding?.edtPassword?.text?.isEmpty() == false) {
                Login()
            } else {
                Toast.makeText(requireContext(), "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding?.root
    }

    private fun Login() {
        // 입력한 이메일과 비밀번호 불러오기
        val email: String = binding?.edtEmail?.text.toString()
        val password: String = binding?.edtPassword?.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful) { // 로그인 성공한 경우
                if (MyApplication.checkAuth()) {
                    MyApplication.email = email
                    db.collection("Users").document(email).get()
                        .addOnSuccessListener { documentSnapshot ->
                            val name: String = documentSnapshot.get("Name") as String // 로그인 시 서버에서 이름 가져오기
                            viewModel.privacy(email, password, name) // 뷰모델에 개인정보 저장
                            findNavController().navigate(R.id.action_loginFragment_to_logoutFragment)
                        }
                }
                // 이메일 인증이 안 된 경우
                else Toast.makeText(requireContext(), "전송된 메일로 이메일 인증이 되지 않았습니다", Toast.LENGTH_SHORT).show()
            }
            // 로그인 실패한 경우
            else Toast.makeText(requireContext(), "이메일 또는 비밀번호가 잘못 입력 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}