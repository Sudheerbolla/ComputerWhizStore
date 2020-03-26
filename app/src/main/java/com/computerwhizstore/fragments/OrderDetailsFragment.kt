package com.computerwhizstore.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.InventoryDetailsAdapter
import com.computerwhizstore.databinding.FragmentCreateOrderBinding
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.models.SalesReportsModel
import com.computerwhizstore.utils.PopUtils
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper
import org.json.JSONArray

class OrderDetailsFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance(orderId: Int): OrderDetailsFragment {
            val addCustomerFragment = OrderDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("orderId", orderId)
            addCustomerFragment.arguments = bundle
            return addCustomerFragment
        }
    }

    private lateinit var dialog: Dialog
    private lateinit var salesReportsModel: SalesReportsModel
    private var addressesModel: AddressesModel? = null
    private var customerModel: CustomersModel? = null
    private var orderId: Int? = 0
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentCreateOrderBinding
    private lateinit var inventorySelectionAdapter: InventoryDetailsAdapter
    private var inventoryModelListArrayList: ArrayList<InventoryModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_order, container, false)
        initComponents()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            if (this != null) {
                if (this.containsKey("orderId")) {
                    orderId = getInt("orderId")
                }
            }
        }
    }

    private fun initComponents() {
        inventoryModelListArrayList = ArrayList()

        binding.txtCreateOrder.visibility = View.GONE
        binding.linButtons.visibility = View.VISIBLE

        binding.txtPrintInvoice.setOnClickListener(this)
        binding.txtPrintPackagingSlip.setOnClickListener(this)

        setOrderDetails()
    }

    private fun setOrderDetails() {
        val dbHelper = DbHelper(mainActivity)
        salesReportsModel = dbHelper.getSalesReportsModel(orderId!!)

        if (!TextUtils.isEmpty(salesReportsModel.productObject)) {
            val productDetailsArray = JSONArray(salesReportsModel.productObject!!)
            for (i in 0 until productDetailsArray.length()) {
                val item = productDetailsArray.getJSONObject(i)
                val prod = dbHelper.getInventoryItem(item.getString("productId"))
                prod.selectedQuantity = item.getInt("quantity")
                inventoryModelListArrayList?.add(prod)
            }
        } else {
            val productDetailsArray = salesReportsModel.productId!!.split(",")
            for (i in productDetailsArray) {
                if (!TextUtils.isEmpty(i)) {
                    val prod = dbHelper.getInventoryItem(i)
                    prod.selectedQuantity = 1
                    inventoryModelListArrayList?.add(prod)
                }
            }
        }

        customerModel = DbHelper(mainActivity).getCustomer(salesReportsModel.customerId!!)

        addressesModel = DbHelper(mainActivity).getCustomerAddress(salesReportsModel.addressId!!)

        binding.txtCustomerDetails.text =
            "${customerModel?.firstName} ${customerModel?.middleName} ${customerModel?.lastName} \n${customerModel?.emailAddress} \n${customerModel?.phoneNumber} "
        binding.txtAddress.text =
            "${addressesModel?.line1} \n${addressesModel?.line2} \n${addressesModel?.city}, ${addressesModel?.province}, ${addressesModel?.zipcode}"
        binding.txtSubTotal.text =
            "${getString(R.string.sub_total)} $ ${salesReportsModel.subTotal}"
        binding.txtTotalQuantity.text =
            "${getString(R.string.total_qty)} ${salesReportsModel.quantity}"
        binding.txtTotalDiscount.text =
            "${getString(R.string.total_discount)} @10% : $ ${salesReportsModel.discount}"
        binding.txtTotalTax.text =
            "${getString(R.string.total_tax)} @12% : $ ${salesReportsModel.tax1}"
        binding.txtTotalPrice.text =
            "${getString(R.string.total_price)} $ ${salesReportsModel.totalAmount}"

        setAddressAdapter()

        ViewCompat.setNestedScrollingEnabled(binding.recyclerViewInventory, false)

        dialog = PopUtils.showPackagingSlip(
            mainActivity, salesReportsModel, customerModel!!, addressesModel!!
        )

    }

    private fun setAddressAdapter() {
        binding.recyclerViewInventory.layoutManager = LinearLayoutManager(mainActivity)
        inventorySelectionAdapter = InventoryDetailsAdapter(inventoryModelListArrayList!!, null)
        binding.recyclerViewInventory.adapter = inventorySelectionAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.order_details))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtPrintInvoice -> {
                StaticUtils.showSimpleToast(mainActivity, "Printed Invoice Successfully")
            }
            R.id.txtPrintPackagingSlip -> {
                if (dialog != null && !dialog.isShowing) dialog.show()
            }
        }
    }

}
