package com.computerwhizstore.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.databinding.FragmentAddAddressBinding
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper

class AddAddressFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance(customerId: Int, addressesModel: AddressesModel?): AddAddressFragment {
            val addCustomerFragment = AddAddressFragment()
            val bundle = Bundle()
            bundle.putInt("customerId", customerId)
            if (addressesModel != null)
                bundle.putParcelable("addressObject", addressesModel)
            addCustomerFragment.arguments = bundle
            return addCustomerFragment
        }
    }

    private var customerId: Int? = null
    private var addressesModel: AddressesModel? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var fragmentAddAddressBinding: FragmentAddAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentAddAddressBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)
        initComponents()
        return fragmentAddAddressBinding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            if (this != null && this.containsKey("addressObject")) {
                addressesModel = getParcelable("addressObject")
            }
            if (this != null && this.containsKey("customerId")) {
                customerId = getInt("customerId", 0)
            }

        }
    }

    private fun initComponents() {
        fragmentAddAddressBinding.txtAddAddress.setOnClickListener(this)
        if (addressesModel != null) {
            fragmentAddAddressBinding.edtLine1.setText(addressesModel?.line1)
            fragmentAddAddressBinding.edtLine2.setText(addressesModel?.line2)
            fragmentAddAddressBinding.edtCity.setText(addressesModel?.city)
            fragmentAddAddressBinding.edtProvince.setText(addressesModel?.province)
            fragmentAddAddressBinding.edtZipCode.setText(addressesModel?.zipcode)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.add_modify_address))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtAddAddress -> {
                val message: String = checkValidations()
                if (TextUtils.isEmpty(message)) {
                    addCustomerRecordToDB()
                } else StaticUtils.showSimpleToast(mainActivity, message)
            }
        }
    }

    private fun addCustomerRecordToDB() {
        if (addressesModel == null || addressesModel?.addressId == null)
            addressesModel = AddressesModel()
        addressesModel?.line1 = fragmentAddAddressBinding.edtLine1.text.toString()
        addressesModel?.line2 = fragmentAddAddressBinding.edtLine2.text.toString()
        addressesModel?.zipcode = fragmentAddAddressBinding.edtZipCode.text.toString()
        addressesModel?.province = fragmentAddAddressBinding.edtProvince.text.toString()
        addressesModel?.city = fragmentAddAddressBinding.edtCity.text.toString()
        addressesModel?.customerId = customerId
        DbHelper(mainActivity).addAddress(addressesModel!!)
        StaticUtils.showSimpleToast(mainActivity, "Record Added Successfully")
        mainActivity.popBackStack()
    }

    private fun checkValidations(): String {
        if (TextUtils.isEmpty(fragmentAddAddressBinding.edtLine1.text.toString())) {
            fragmentAddAddressBinding.edtLine1.requestFocus()
            return "Please Enter Line 1"
        }
        if (TextUtils.isEmpty(fragmentAddAddressBinding.edtLine2.text.toString())) {
            fragmentAddAddressBinding.edtLine2.requestFocus()
            return "Please Enter Line 2"
        }
        if (TextUtils.isEmpty(fragmentAddAddressBinding.edtCity.text.toString())) {
            fragmentAddAddressBinding.edtCity.requestFocus()
            return "Please Enter City"
        }
        if (TextUtils.isEmpty(fragmentAddAddressBinding.edtProvince.text.toString())) {
            fragmentAddAddressBinding.edtProvince.requestFocus()
            return "Please Enter Province"
        }
        if (TextUtils.isEmpty(fragmentAddAddressBinding.edtZipCode.text.toString()) || fragmentAddAddressBinding.edtZipCode.text.toString().length < 6) {
            fragmentAddAddressBinding.edtZipCode.requestFocus()
            return "Please Enter Zip Code"
        }
        return ""
    }
}
