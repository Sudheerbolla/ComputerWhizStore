package com.computerwhizstore.fragments

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.CategoriesSpinnerAdapter
import com.computerwhizstore.adapters.SubCategoriesSpinnerAdapter
import com.computerwhizstore.databinding.FragmentAddInventoryBinding
import com.computerwhizstore.models.CategoryModel
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.models.SubCategoryModel
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper
import java.util.*

class AddInventoryFragment : BaseFragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(inventoryModel: InventoryModel): AddInventoryFragment {
            val addInventoryFragment = AddInventoryFragment()
            val bundle = Bundle()
            bundle.putParcelable("inventoryObject", inventoryModel)
            addInventoryFragment.arguments = bundle
            return addInventoryFragment
        }
    }

    private var inventoryModel: InventoryModel? = null
    private var categoryId: Int? = null
    private var subCategoryId: Int? = null
    private lateinit var categories: ArrayList<CategoryModel>
    private lateinit var subCategories: ArrayList<SubCategoryModel>
    private lateinit var mainActivity: MainActivity
    private lateinit var fragmentAddInventoryBinding: FragmentAddInventoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentAddInventoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_inventory, container, false)
        initComponents()
        return fragmentAddInventoryBinding.root
    }

    private fun initComponents() {
        fragmentAddInventoryBinding.txtAddInventory.setOnClickListener(this)
        setUpCategories()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            if (this?.containsKey("inventoryObject")!!) {
                inventoryModel = getParcelable("inventoryObject")
            }
        }
    }

    private fun setUpCategories() {
        categories = StaticUtils.getCategories()
        val array = arrayOfNulls<CategoryModel>(categories.size)
        categories.toArray(array)
        val dataAdapter =
            CategoriesSpinnerAdapter(
                mainActivity,
                android.R.layout.simple_spinner_item,
                array as Array<out CategoryModel>?
            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentAddInventoryBinding.spinnerCategories.adapter = dataAdapter
        fragmentAddInventoryBinding.spinnerCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    categoryId = categories.get(position).categoryId!!
                    setUpSubCategories(categoryId!!)
                }

            }
    }

    private fun setUpSubCategories(catId: Int) {
        subCategories = StaticUtils.getSubCategories(catId)
        val array = arrayOfNulls<SubCategoryModel>(subCategories.size)
        subCategories.toArray(array)
        val dataAdapter =
            SubCategoriesSpinnerAdapter(
                mainActivity, android.R.layout.simple_spinner_item,
                array as Array<out SubCategoryModel>?
            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentAddInventoryBinding.spinnerSubCategories.adapter = dataAdapter
        fragmentAddInventoryBinding.spinnerSubCategories.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    subCategoryId = subCategories.get(position).subCategoryId!!
                }

            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) mainActivity = context
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(getString(R.string.add_modify_inventory))
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txtAddInventory -> {
                val message: String = checkValidations()
                if (TextUtils.isEmpty(message)) {
                    addInventoryRecordToDB()
                } else StaticUtils.showSimpleToast(mainActivity, message)
            }
        }
    }

    private fun addInventoryRecordToDB() {
        val inventoryModel = InventoryModel()
        inventoryModel.productName = fragmentAddInventoryBinding.edtName.text.toString()
        inventoryModel.productDescription =
            fragmentAddInventoryBinding.edtDescription.text.toString()
        inventoryModel.unitPrice = fragmentAddInventoryBinding.edtPrice.text.toString().toDouble()
        inventoryModel.quantity = fragmentAddInventoryBinding.edtQuantity.text.toString().toInt()
        inventoryModel.brand = fragmentAddInventoryBinding.edtBrand.text.toString()
        inventoryModel.categoryId = categoryId
        inventoryModel.subCategoryId = subCategoryId
        DbHelper(mainActivity).addInventory(inventoryModel)
        StaticUtils.showSimpleToast(mainActivity, "Record Added Successfully")
        mainActivity.popBackStack()
    }

    private fun checkValidations(): String {
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.edtName.text.toString())) {
            fragmentAddInventoryBinding.edtName.requestFocus()
            return "Please Enter Product Name"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.edtDescription.text.toString())) {
            fragmentAddInventoryBinding.edtDescription.requestFocus()
            return "Please Enter Product Description"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.edtPrice.text.toString())) {
            fragmentAddInventoryBinding.edtPrice.requestFocus()
            return "Please Enter Product Price"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.edtBrand.text.toString())) {
            fragmentAddInventoryBinding.edtBrand.requestFocus()
            return "Please Enter Product Brand"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.edtQuantity.text.toString())) {
            fragmentAddInventoryBinding.edtQuantity.requestFocus()
            return "Please Enter Available Quantity"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.spinnerCategories.selectedItem.toString())) {
            fragmentAddInventoryBinding.spinnerCategories.requestFocus()
            return "Please Select Category"
        }
        if (TextUtils.isEmpty(fragmentAddInventoryBinding.spinnerSubCategories.selectedItem.toString())) {
            fragmentAddInventoryBinding.spinnerSubCategories.requestFocus()
            return "Please Select Sub Category"
        }
        return ""
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.spinnerCategories -> {
                categoryId = categories.get(position).categoryId!!
                setUpSubCategories(categoryId!!)
            }
            R.id.spinnerSubCategories -> {
                subCategoryId = subCategories.get(position).subCategoryId!!
            }
        }
    }
}
