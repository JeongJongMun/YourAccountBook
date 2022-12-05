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

        binding?.recRanking?.layoutManager = LinearLayoutManager(context)
        binding?.recRanking?.adapter = RankingAdapter()

        binding?.searchBtn?.setOnClickListener {
            // RankingAdapter 내에 있는 search 함수를 불러와 검색 기능을 활성화
            (binding?.recRanking?.adapter as RankingAdapter).search(binding?.searchWord?.text.toString())
        }
    }
}