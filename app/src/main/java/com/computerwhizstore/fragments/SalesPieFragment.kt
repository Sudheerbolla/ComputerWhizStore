package com.computerwhizstore.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.databinding.FragmentSalesPieBinding
import com.computerwhizstore.utils.Constants
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

class SalesPieFragment : BaseFragment(), View.OnClickListener {
    private var tf: Typeface? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var fragmentDashboardBinding: FragmentSalesPieBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        tf = Typeface.createFromAsset(context!!.assets, "OpenSans-Regular.ttf")

        fragmentDashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sales_pie, container, false)
        initComponents()
        return fragmentDashboardBinding.root
    }

    private fun initComponents() {
        fragmentDashboardBinding.pieChart1.getDescription().setEnabled(false)
        fragmentDashboardBinding.pieChart1.setCenterTextTypeface(tf)
        fragmentDashboardBinding.pieChart1.setCenterText(generateCenterText())
        fragmentDashboardBinding.pieChart1.setCenterTextSize(10f)
        fragmentDashboardBinding.pieChart1.setCenterTextTypeface(tf)

        // radius of the center hole in percent of maximum radius

        // radius of the center hole in percent of maximum radius
        fragmentDashboardBinding.pieChart1.setHoleRadius(45f)
        fragmentDashboardBinding.pieChart1.setTransparentCircleRadius(50f)

        val l: Legend = fragmentDashboardBinding.pieChart1.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)

        fragmentDashboardBinding.pieChart1.setData(generatePieData())

    }

    private fun generateCenterText(): SpannableString? {
        val s = SpannableString("Sales by percentage this week")
        s.setSpan(RelativeSizeSpan(2f), 0, 8, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 8, s.length, 0)
        return s
    }

    protected fun generatePieData(): PieData? {
        val count = 5
        val entries1 = ArrayList<PieEntry>()
        entries1.add(PieEntry(25.toFloat(), "Mobiles "))
        entries1.add(PieEntry(30.toFloat(), "Computers And Laptops "))
        entries1.add(PieEntry(15.toFloat(), "Printers "))
        entries1.add(PieEntry(10.toFloat(), "Cameras "))
        entries1.add(PieEntry(20.toFloat(), "Other Accessories "))
//        for (i in 0 until count) {
//            entries1.add(PieEntry((Math.random() * 60 + 40).toFloat(), "Quarter " + (i + 1)))
//        }
        val ds1 = PieDataSet(entries1, "Sales by percentage this week")
        ds1.setColors(*ColorTemplate.COLORFUL_COLORS)
        ds1.sliceSpace = 2f
        ds1.valueTextColor = Color.WHITE
        ds1.valueTextSize = 12f
        val d = PieData(ds1)
        d.setValueTypeface(tf)
        return d
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.sales_overview))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtSalesReport -> {
                mainActivity.replaceFragment(ListFragment.newInstance(Constants.SCREEN_SALES), true)
            }
            R.id.txtCustomerReports -> {
                mainActivity.replaceFragment(
                    ListFragment.newInstance(Constants.SCREEN_CUSTOMERS),
                    true
                )
            }
//            R.id.txtPackagingSlips -> {
//                mainActivity.replaceFragment(
//                    ListFragment.newInstance(Constants.SCREEN_PACKAGING_SLIPS),
//                    true
//                )
//            }
//            R.id.txtInvoices -> {
//                mainActivity.replaceFragment(
//                    ListFragment.newInstance(Constants.SCREEN_INVOICES),
//                    true
//                )
//            }
            R.id.txtInventory -> {
                mainActivity.replaceFragment(
                    ListFragment.newInstance(Constants.SCREEN_INVENTORY),
                    true
                )
            }
        }
    }
}
