package com.computerwhizstore.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.InventorySelectionAdapter
import com.computerwhizstore.databinding.FragmentCreateListBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.StaticUtils.Companion.isGreaterThan
import com.computerwhizstore.utils.StaticUtils.Companion.isLessThan
import com.computerwhizstore.utils.dbutils.DbHelper

class AddSalesFragment : BaseFragment(), View.OnClickListener, IClickListener {

    companion object {
        fun newInstance(/*customersModel: CustomersModel*/): AddSalesFragment {
            val addCustomerFragment = AddSalesFragment()
            val bundle = Bundle()
//            bundle.putParcelable("customerObject", customersModel)
            addCustomerFragment.arguments = bundle
            return addCustomerFragment
        }
    }

    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentCreateListBinding
    private lateinit var inventorySelectionAdapter: InventorySelectionAdapter
    private var inventoryModelListArrayList: ArrayList<InventoryModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_list, container, false)
        initComponents()
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments.apply {
//            if (this != null && this.containsKey("customerObject")) {
//                customersModel = getParcelable("customerObject")
//            }
//        }
    }

    private fun initComponents() {
        binding.linCreateOrder.visibility = View.GONE
        binding.linCreateOrder.setOnClickListener(this)
        inventoryModelListArrayList = ArrayList()
        inventoryModelListArrayList!!.addAll(DbHelper(mainActivity).getInventoryList)
        setAddressAdapter()
        binding.progressBar.visibility = View.GONE
        binding.txtNoDataFound.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.linCreateOrder.visibility = View.VISIBLE
    }

    private fun setAddressAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        inventorySelectionAdapter = InventorySelectionAdapter(inventoryModelListArrayList!!, this)
        binding.recyclerView.adapter = inventorySelectionAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.create_order))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.lin_create_order -> {
                val message: String = checkValidations()
                if (TextUtils.isEmpty(message)) {
                    navigateToNextScreen()
                } else StaticUtils.showSimpleToast(mainActivity, message)
            }
        }
    }

    private fun navigateToNextScreen() {
        val selectedInv = ArrayList<InventoryModel>();
        for (inv in inventoryModelListArrayList!!) {
            if (inv.selectedQuantity.isGreaterThan(0)) selectedInv.add(inv)
        }
        mainActivity.replaceFragment(
            CreateOrderFragment.newInstance(
                selectedInv, totalPrice, totalQty
            )
        )
    }

    private fun checkValidations(): String {
        if (totalPrice.compareTo(0.0).isLessThan(0) ||
            totalPrice.compareTo(0.0).equals(0) || totalQty.compareTo(0)
                .isLessThan(0) || totalQty.compareTo(0).equals(0)
        ) {
            return "Please select atleast one product to Proceed further."
        }
        return ""
    }

    override fun onClick(view: View, position: Int) {
        when (view.id) {
            R.id.txtMinus -> {
                if (inventoryModelListArrayList?.get(position)?.selectedQuantity?.compareTo(0) != 0) {
                    inventoryModelListArrayList?.get(position)?.selectedQuantity =
                        inventoryModelListArrayList?.get(position)?.selectedQuantity?.minus(1)
                    inventorySelectionAdapter.notifyDataSetChanged()
                    updateTotalPriceAndQuantity()
                }
            }
            R.id.txtPlus -> {
                if (inventoryModelListArrayList?.get(position)?.selectedQuantity?.equals(
                        inventoryModelListArrayList?.get(position)?.quantity!!
                    )!!
                ) {
                    StaticUtils.showSimpleToast(
                        mainActivity, "Quantity excess available quanity."
                    )
                } else {
                    inventoryModelListArrayList?.get(position)?.selectedQuantity =
                        inventoryModelListArrayList?.get(position)?.selectedQuantity?.plus(1)
                    inventorySelectionAdapter.notifyDataSetChanged()
                    updateTotalPriceAndQuantity()
                }
            }
        }
    }

    var totalPrice = 0.0
    var totalQty = 0

    private fun updateTotalPriceAndQuantity() {
        totalPrice = 0.0
        totalQty = 0
        for (invItem in inventoryModelListArrayList!!) {
            if (invItem.selectedQuantity.isGreaterThan(0)) {
                totalPrice = totalPrice.plus(invItem.selectedQuantity!! * invItem.unitPrice!!)
                totalQty = totalQty.plus(invItem.selectedQuantity!!)
            }
        }
        binding.txtTotalPrice.text = "${getString(R.string.total_price)} $ $totalPrice"
        binding.txtTotalQuantity.text = "${getString(R.string.total_qty)} $totalQty"
    }

    override fun onLongClick(view: View, position: Int) {

    }

}
