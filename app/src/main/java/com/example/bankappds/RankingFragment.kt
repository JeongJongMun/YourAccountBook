package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentRankingBinding

class RankingFragment : Fragment() {
    var binding: FragmentRankingBinding? = null

    // 첫 화면에서 뜨는 spinner 목록은 이름

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RankingFragment 실행 시 Firestore Database에 있는 모든 사용자의 이름, 총 지출, 총 정기지출 데이터를 불러온다.
        binding?.recRanking?.layoutManager = LinearLayoutManager(context)
        binding?.recRanking?.adapter = RankingAdapter()

        // 검색 기능을 사용할 경우 (검색 내용은 사용자 이름)
        binding?.searchBtn?.setOnClickListener {
            // RankingAdapter 내에 있는 search 함수를 불러와 검색 기능을 활성화
            (binding?.recRanking?.adapter as RankingAdapter).search(binding?.searchWord?.text.toString())
        }
    }
}