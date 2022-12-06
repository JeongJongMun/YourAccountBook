package com.example.bankappds

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bankappds.databinding.RankingListBinding
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("NotifyDataSetChanged")
class RankingAdapter
    : RecyclerView.Adapter<RankingAdapter.Holder>() {
    // 리사이클러뷰로 출력할 데이터를 받기 위해 ArrayList를 선언
    val userData : ArrayList<FireStoreData> = arrayListOf()

    // Firestore Database에서 데이터를 불러오기 위해 FirebaseFirestore를 선언
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // 첫 화면에 모든 목록을 띄울 준비
    init {  // users의 문서를 불러온 뒤 person으로 변환해 ArrayList에 담는다
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            userData.clear()
            // userData 안에 cloud firestore에 담긴 모든 정보가 들어온다
            if (querySnapshot != null) {
                for (snapshot in querySnapshot.documents) {
                    val item = snapshot.toObject(FireStoreData::class.java)
                    if (item != null) {
                        userData.add(item)
                    }
                }
            }

            // 내림차순으로 정렬
            userData.sortByDescending { it.MonthExpense }
            notifyDataSetChanged()
        }
    }

    // 검색 기능을 사용하는 함수
    fun search(searchWord : String) {
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            userData.clear()
            if (querySnapshot != null) {
                for (snapshot in querySnapshot.documents) {
                    // 우선 Firestore Database에 있는 데이터 중 "Name"을 키로 갖는 데이터만 불러온다
                    // 그리고 그 중 searchWord와 일치하는 데이터를 ArrayList에 넣는다.
                    if (snapshot.getString("Name")?.contains(searchWord) == true) {
                        val item = snapshot.toObject(FireStoreData::class.java)
                        if (item != null) {
                            userData.add(item)
                        }
                    }
                }
            }
            userData.sortByDescending { it.MonthExpense }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RankingListBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(userData[position], position)
    }

    override fun getItemCount() = userData.size

    class Holder(private val binding : RankingListBinding): ViewHolder(binding.root) {
        fun bind(user: FireStoreData, position: Int) {
            binding.txtName.text = user.Name
            binding.txtMonthexpense.text = user.MonthExpense.toString()
            binding.txtRank.text = (position+1).toString()
        }
    }
}