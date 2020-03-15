package com.computerwhizstore.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.AddressAdapter
import com.computerwhizstore.databinding.FragmentAddCustomerBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper

class AddCustomerFragment : BaseFragment(), View.OnClickListener, IClickListener {

    companion object {
        fun newInstance(customersModel: CustomersModel): AddCustomerFragment {
            val addCustomerFragment = AddCustomerFragment()
            val bundle = Bundle()
            bundle.putParcelable("customerObject", customersModel)
            addCustomerFragment.arguments = bundle
            return addCustomerFragment
        }
    }

    private var customersModel: CustomersModel? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var addressArrayList: ArrayList<AddressesModel>
    private lateinit var fragmentAddCustomersBinding: FragmentAddCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentAddCustomersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_customer, container, false)
        initComponents()
        return fragmentAddCustomersBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            if (this != null && this.containsKey("customerObject")) {
                customersModel = getParcelable("customerObject")
            }
        }
    }

    private fun initComponents() {
        fragmentAddCustomersBinding.txtAddCustomer.setOnClickListener(this)
        if (customersModel == null) {
            fragmentAddCustomersBinding.linAddress.visibility = View.GONE
        } else {
            fragmentAddCustomersBinding.linAddress.visibility = View.VISIBLE
            addressArrayList = ArrayList()
            setAddressAdapter()
            addressArrayList.addAll(DbHelper(mainActivity).getAddressList)
            fragmentAddCustomersBinding.txtAddAddress.setOnClickListener(this)
        }
        if (customersModel != null) {
            fragmentAddCustomersBinding.edtPhone.setText(customersModel?.phoneNumber)
            fragmentAddCustomersBinding.edtEmailAddress.setText(customersModel?.emailAddress)
            fragmentAddCustomersBinding.edtFirstName.setText(customersModel?.firstName)
            fragmentAddCustomersBinding.edtLastName.setText(customersModel?.lastName)
            fragmentAddCustomersBinding.edtMiddleName.setText(customersModel?.middleName)
        }
    }

    private fun setAddressAdapter() {
        fragmentAddCustomersBinding.recyclerViewAddress.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        fragmentAddCustomersBinding.recyclerViewAddress.adapter =
            AddressAdapter(addressArrayList, this)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.add_modify_customer))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtAddCustomer -> {
                val message: String = checkValidations()
                if (TextUtils.isEmpty(message)) {
                    addCustomerRecordToDB()
                } else StaticUtils.showSimpleToast(mainActivity, message)
            }
            R.id.txtAddAddress -> {
                mainActivity.replaceFragment(
                    AddAddressFragment.newInstance(
                        customersModel?.customerId!!, null
                    )
                )
            }
        }
    }

    private fun addCustomerRecordToDB() {
        if (customersModel == null || customersModel?.customerId == null)
            customersModel = CustomersModel()
        customersModel?.firstName = fragmentAddCustomersBinding.edtFirstName.text.toString()
        customersModel?.middleName = fragmentAddCustomersBinding.edtMiddleName.text.toString()
        customersModel?.lastName = fragmentAddCustomersBinding.edtLastName.text.toString()
        customersModel?.phoneNumber = fragmentAddCustomersBinding.edtPhone.text.toString()
        customersModel?.emailAddress = fragmentAddCustomersBinding.edtEmailAddress.text.toString()
        DbHelper(mainActivity).addCustomer(customersModel!!)
        StaticUtils.showSimpleToast(mainActivity, "Record Added Successfully")
        mainActivity.popBackStack()
    }

    private fun checkValidations(): String {
        if (TextUtils.isEmpty(fragmentAddCustomersBinding.edtFirstName.text.toString())) {
            fragmentAddCustomersBinding.edtFirstName.requestFocus()
            return "Please Enter First Name"
        }
        if (TextUtils.isEmpty(fragmentAddCustomersBinding.edtLastName.text.toString())) {
            fragmentAddCustomersBinding.edtLastName.requestFocus()
            return "Please Enter Last Name"
        }
        if (TextUtils.isEmpty(fragmentAddCustomersBinding.edtPhone.text.toString())) {
            fragmentAddCustomersBinding.edtPhone.requestFocus()
            return "Please Enter Phone Number"
        }
        if (!TextUtils.isEmpty(fragmentAddCustomersBinding.edtEmailAddress.text.toString())) {
            if (!Patterns.EMAIL_ADDRESS.matcher(
                    fragmentAddCustomersBinding.edtEmailAddress.text.toString().trim()
                ).matches()
            ) {
                fragmentAddCustomersBinding.edtEmailAddress.requestFocus()
                return "Please Enter valid Email Address"
            }
        }
        return ""
    }

    override fun onClick(view: View, position: Int) {
        mainActivity.replaceFragment(
            AddAddressFragment.newInstance(
                customersModel?.customerId!!, addressArrayList.get(position)
            )
        )
    }

    override fun onLongClick(view: View, position: Int) {

    }

}
