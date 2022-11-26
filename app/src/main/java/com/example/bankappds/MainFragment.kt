package com.example.bankappds

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.bankappds.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    // 프래그먼트에서 context 사용 가능
    lateinit var mainActivity: MainActivity

    private var binding : FragmentMainBinding? = null
    private var position = FIRST_POSITION
    companion object {
        const val FIRST_POSITION = 1
        const val SECOND_POSITION = 2
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().replace(R.id.frm_fragment, DayCalendarFragment()).commit()

        binding?.imgBtnProfile?.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            when(position) {
                FIRST_POSITION -> {
                    transaction.replace(R.id.frm_fragment, MonthFragment()).commit()
                    position = SECOND_POSITION
                }
                SECOND_POSITION -> {
                    transaction.replace(R.id.frm_fragment, DayCalendarFragment()).commit()
                    position = FIRST_POSITION
                }
            }
        }
    }



}