package com.computerwhizstore.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.InventoryDetailsAdapter
import com.computerwhizstore.databinding.FragmentCreateOrderBinding
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.models.SalesReportsModel
import com.computerwhizstore.utils.Constants
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CreateOrderFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance(
            inventoryArrayList: ArrayList<InventoryModel>,
            totalPrice: Double, totalQuantity: Int
        ): CreateOrderFragment {
            val addCustomerFragment = CreateOrderFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("inventoryArrayList", inventoryArrayList)
            bundle.putInt("totalQuantity", totalQuantity)
            bundle.putDouble("totalPrice", totalPrice)
            addCustomerFragment.arguments = bundle
            return addCustomerFragment
        }
    }

    private var addressesModel: AddressesModel? = null
    private var tax: Double? = 0.0
    private var discount: Double? = 0.0
    private var finalPrice: Double? = 0.0
    private var customerModel: CustomersModel? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentCreateOrderBinding
    private var totalQuantity: Int? = 0
    private var totalPrice: Double? = 0.0
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
        inventoryModelListArrayList = ArrayList()
        arguments.apply {
            if (this != null) {
                if (this.containsKey("inventoryArrayList")) {
                    inventoryModelListArrayList = getParcelableArrayList("inventoryArrayList")
                }
                if (this.containsKey("totalQuantity")) {
                    totalQuantity = getInt("totalQuantity")
                }
                if (this.containsKey("totalPrice")) {
                    totalPrice = getDouble("totalPrice")
                }
            }
        }
    }

    private fun initComponents() {
        binding.txtCreateOrder.visibility = View.VISIBLE
        binding.linButtons.visibility = View.GONE

        binding.txtCreateOrder.setOnClickListener(this)
        binding.txtCustomerDetails.setOnClickListener(this)
        binding.txtAddress.setOnClickListener(this)
        setAddressAdapter()
        tax = ((12 * totalPrice!!) / 100)
        discount = ((10 * totalPrice!!) / 100)
        finalPrice = (totalPrice!!.plus(tax!!).minus(discount!!))
        binding.txtSubTotal.text = "${getString(R.string.sub_total)} $ ${totalPrice}"
        binding.txtTotalQuantity.text = "${getString(R.string.total_qty)} ${totalQuantity}"
        binding.txtTotalDiscount.text = "${getString(R.string.total_discount)} @10% : $ ${discount}"
        binding.txtTotalTax.text = "${getString(R.string.total_tax)} @12% : $ ${tax}"
        binding.txtTotalPrice.text = "${getString(R.string.total_price)} $ ${finalPrice}"

        ViewCompat.setNestedScrollingEnabled(binding.recyclerViewInventory, false)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                customerModel = data?.getParcelableExtra("selectedCustomer")!!
                binding.txtCustomerDetails.text =
                    "${customerModel?.firstName} ${customerModel?.middleName} ${customerModel?.lastName} \n${customerModel?.emailAddress} \n${customerModel?.phoneNumber} "
                val addressArrayList =
                    DbHelper(mainActivity).getAddressList(customerModel?.customerId!!)
                if (addressArrayList != null && addressArrayList.isNotEmpty()) {
                    addressesModel = addressArrayList.get(0)
                    binding.txtAddress.text =
                        "${addressesModel?.line1} \n${addressesModel?.line2} \n${addressesModel?.city}, ${addressesModel?.province}, ${addressesModel?.zipcode}"
                }
            } else if (requestCode == 2) {
                addressesModel = data?.getParcelableExtra("addressesModel")!!
                binding.txtAddress.text =
                    "${addressesModel?.line1} \n${addressesModel?.line2} \n${addressesModel?.city}, ${addressesModel?.province}, ${addressesModel?.zipcode}"
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtCustomerDetails -> {
                val frag: Fragment = ListFragment.newInstance(Constants.SCREEN_SELECT_CUSTOMERS)
                frag.setTargetFragment(this, 1)
                mainActivity.addFragment(frag, true)
            }
            R.id.txtCreateOrder -> {
                val message = checkValidations()
                if (TextUtils.isEmpty(message)) {
                    addToSalesRecordToDatabase()
                } else StaticUtils.showSimpleToast(mainActivity, message)
            }
            R.id.txtAddress -> {
                if (customerModel == null || customerModel?.customerId == null) {
                    StaticUtils.showSimpleToast(
                        mainActivity, "Please Select Customer before selecting Address"
                    )
                } else {
                    val frag: Fragment = AddAddressFragment.newInstance(
                        customerModel?.customerId!!, addressesModel, true
                    )
                    frag.setTargetFragment(this, 2)
                    mainActivity.addFragment(frag, true)
                }
            }
        }
    }

    private fun addToSalesRecordToDatabase() {
        val productInfoArray = JSONArray()
        var pid = ""
        var name = ""
        for (inv in inventoryModelListArrayList!!) {
            pid = pid + "," + inv.inventoryId
            name = name + "," + inv.productName
            val productInfoObject = JSONObject()
            productInfoObject.put("subCategoryId", inv.subCategoryId)
            productInfoObject.put("categoryId", inv.categoryId)
            productInfoObject.put("productId", inv.inventoryId)
            productInfoObject.put("quantity", inv.selectedQuantity)
            productInfoObject.put("name", inv.productName)
            productInfoObject.put("totalPrice", (inv.selectedQuantity!! * inv.unitPrice!!))
            productInfoArray.put(productInfoObject)
        }
        val salesReportsModel = SalesReportsModel()
        salesReportsModel.timeStamp = Calendar.getInstance().timeInMillis
        salesReportsModel.discount = discount
        salesReportsModel.totalAmount = finalPrice
        salesReportsModel.salesPersonId = MainActivity.userModel.userId!!
        salesReportsModel.subTotal = totalPrice
        salesReportsModel.customerId = customerModel?.customerId
        salesReportsModel.addressId = addressesModel?.addressId
        salesReportsModel.productId = pid
        salesReportsModel.productObject = productInfoArray.toString()
        salesReportsModel.tax1 = tax
        salesReportsModel.quantity = totalQuantity
        salesReportsModel.name = name
        DbHelper(mainActivity).addSalesReport(salesReportsModel)

        StaticUtils.showSimpleToast(mainActivity, "Order Created Successfully.")

        mainActivity.clearBackStack()
        mainActivity.replaceFragment(DashboardFragment(), false)

    }

    private fun checkValidations(): String {
        if (customerModel == null || customerModel?.customerId == null) return "Please Select Customer"
        if (addressesModel == null || addressesModel?.addressId == null) return "Please Select Address"
        return ""
    }

}
