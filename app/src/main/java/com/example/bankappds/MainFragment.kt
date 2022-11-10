package com.example.bankappds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankappds.databinding.FragmentMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    var binding : FragmentMainBinding? = null
    private lateinit var adapter: MainListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainArrayList : Array<MainList>

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MainListAdapter(mainArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize() {
        mainArrayList = arrayOf(
        MainList(2022, 11,1,1000000,15000),
        MainList(2022, 11,2,1000000,15000),
        MainList(2022, 11,3,1000000,15000),
        MainList(2022, 11,4,1000000,15000),
        MainList(2022, 11,5,1000000,15000),
        MainList(2022, 11,6,1000000,15000),
        MainList(2022, 11,7,1000000,15000),
        MainList(2022, 11,8,1000000,15000),
        MainList(2022, 11,9,1000000,15000),
        MainList(2022, 11,10,1000000,15000),
        MainList(2022, 11,11,1000000,15000),
        MainList(2022, 11,12,1000000,15000),
        MainList(2022, 11,13,1000000,15000),
        MainList(2022, 11,14,1000000,15000),
        MainList(2022, 11,15,1000000,15000),
        MainList(2022, 11,16,1000000,15000)
        )
    }
}