package com.computerwhizstore.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.databinding.FragmentDashboardBinding
import com.computerwhizstore.utils.Constants

class DashboardFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mainActivity: MainActivity
    private lateinit var fragmentDashboardBinding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentDashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        initComponents()
        return fragmentDashboardBinding.root
    }

    private fun initComponents() {
        fragmentDashboardBinding.txtPackagingSlips.visibility = View.GONE
        fragmentDashboardBinding.txtInvoices.visibility = View.GONE
        if (MainActivity.userModel.userType == 0) {
            fragmentDashboardBinding.txtSalesOverview.visibility = View.VISIBLE
            fragmentDashboardBinding.txtSalesOverview.setOnClickListener(this)
        } else fragmentDashboardBinding.txtSalesOverview.visibility = View.GONE

        fragmentDashboardBinding.txtCustomerReports.setOnClickListener(this)
        fragmentDashboardBinding.txtSalesReport.setOnClickListener(this)
        fragmentDashboardBinding.txtPackagingSlips.setOnClickListener(this)
        fragmentDashboardBinding.txtInvoices.setOnClickListener(this)
        fragmentDashboardBinding.txtInventory.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.app_name))
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
            R.id.txtSalesOverview -> {
                mainActivity.replaceFragment(SalesPieFragment(), true)
            }
            R.id.txtInventory -> {
                mainActivity.replaceFragment(
                    ListFragment.newInstance(Constants.SCREEN_INVENTORY),
                    true
                )
            }
        }
    }
}
