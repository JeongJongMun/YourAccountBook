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
    val userData : ArrayList<FireStoreData> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // 첫 화면에 모든 목록을 띄울 준비
    init {  // users의 문서를 불러온 뒤 person으로 변환해 ArrayList에 담는다
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList를 초기화
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
            userData.sortByDescending { it.TotalExpense }
            notifyDataSetChanged()
        }
    }

    // 검색 기능을 사용하는 함수
    fun search(searchWord : String, option : String) {
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            userData.clear()

            if (querySnapshot != null) {
                for (snapshot in querySnapshot.documents) {
                    if (snapshot.getString(option)?.contains(searchWord) == true) {
                        val item = snapshot.toObject(FireStoreData::class.java)
                        if (item != null) {
                            userData.add(item)
                        }
                    }
                }
            }
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
            binding.txtTotalExpense.text = user.TotalExpense.toString()
            binding.txtTotalRegExpense.text = user.RegTotalExpense.toString()
            binding.txtRank.text = (position+1).toString()
        }
    }
}