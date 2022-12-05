package com.example.bankappds

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bankappds.databinding.FragmentCircleChartBinding
import com.example.bankappds.viewmodel.DataViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import kotlin.collections.ArrayList


class CircleChart : Fragment() {
    var binding: FragmentCircleChartBinding? = null
    lateinit var pieChart: PieChart

    val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCircleChartBinding.inflate(inflater, container, false)
        setChartData()

        return binding?.root
    }


    private fun setChartData() {
        pieChart = binding!!.circleChart

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = true
        pieChart.description.text = "카테고리 별 지출 금액" // 차트 제목
        pieChart.description.textColor = Color.WHITE
        pieChart.description.setPosition(840F,200F) // 제목 위치
        pieChart.description.textSize = 30F // 제목 크기
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f

        pieChart.setDrawCenterText(true)

        pieChart.rotationAngle = 0f

        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.legend.isEnabled = true
        pieChart.legend.textSize = 15F
        pieChart.legend.isWordWrapEnabled = true; // 레전드 넘어가면 줄 넘김
        pieChart.setEntryLabelColor(Color.BLACK) // 차트 내 카테고리 색깔
        pieChart.setEntryLabelTextSize(12f) // 차트 내 글자 크기


        val entries: ArrayList<PieEntry> = ArrayList()
        if (viewModel.getArraybyCategory(Ecategory.FOOD) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.FOOD).toFloat(), "FOOD"))
        }
        if (viewModel.getArraybyCategory(Ecategory.ENTERTAIN) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.ENTERTAIN).toFloat(), "ENTERTAIN"))
        }
        if (viewModel.getArraybyCategory(Ecategory.SHOPPING) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.SHOPPING).toFloat(), "SHOPPING"))
        }
        if (viewModel.getArraybyCategory(Ecategory.HOBBY) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.HOBBY).toFloat(), "HOBBY"))
        }
        if (viewModel.getArraybyCategory(Ecategory.HEALTH) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.HEALTH).toFloat(), "HEALTH"))
        }
        if (viewModel.getArraybyCategory(Ecategory.FINANCE) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.FINANCE).toFloat(), "FINANCE"))
        }
        if (viewModel.getArraybyCategory(Ecategory.HOME) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.HOME).toFloat(), "HOME"))
        }
        if (viewModel.getArraybyCategory(Ecategory.ETC) != 0) {
            entries.add(PieEntry(viewModel.getArraybyCategory(Ecategory.ETC).toFloat(), "ETC"))
        }

        val dataSet = PieDataSet(entries, "Category")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.yellow))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.green))
        colors.add(resources.getColor(R.color.blue))
        colors.add(resources.getColor(R.color.grey))
        colors.add(resources.getColor(R.color.brown))
        colors.add(resources.getColor(R.color.purple))

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f) // 숫자 크기
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.BLACK) // 숫자 색깔
        pieChart.data = data

        pieChart.highlightValues(null)

        pieChart.invalidate()
    }

}