package com.example.bankappds

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankappds.databinding.FragmentFixedBinding
import com.example.bankappds.databinding.FragmentRegBinding


class RegFragment : Fragment() {
    var binding : FragmentRegBinding?= null

    var payLists : ArrayList<FixedPay> = arrayListOf(
        FixedPay(Ecategory.ENTE,"Netflix",12000),
        FixedPay(Ecategory.HOME,"전기세",30000),
        FixedPay(Ecategory.FOOD,"급식비",300000)
    )

    val args: RegFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegBinding.inflate(inflater)

        binding?.recPay?.layoutManager = LinearLayoutManager(activity) //context
        binding?.recPay?.setHasFixedSize(true)
        binding?.recPay?.adapter=FixedPayAdapter(payLists)

        //https://ddolcat.tistory.com/592

        return binding?.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnKkk?.setOnClickListener {
            payLists.add(FixedPay(Ecategory.MONEY,"gg",123))
            // 리사이클러뷰가 변경되었음을 알림
            binding?.recPay?.adapter?.notifyDataSetChanged()
        }

        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_regFragment_to_regInputFragment)
        }

        // 고정지출 입력 확인
/*            payLists.add(args.inputFix!!)
            binding?.recPay?.adapter?.notifyDataSetChanged()*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}