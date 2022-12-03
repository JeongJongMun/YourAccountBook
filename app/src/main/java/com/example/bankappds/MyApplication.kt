package com.example.bankappds

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication: MultiDexApplication() {
    companion object {
        var auth: FirebaseAuth = Firebase.auth

        // 로그인 확인 여부로 사용하는 변수
        var email: String? = null

        // 로그인 여부를 확인 하는 함수
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser // firebase에 등록한 유저 정보 불러오기
            return currentUser?.let {
                email = currentUser.email // 유저 정보가 있으면 email 가져옥

                // 로그인 되어있는 경우
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }

    }

}