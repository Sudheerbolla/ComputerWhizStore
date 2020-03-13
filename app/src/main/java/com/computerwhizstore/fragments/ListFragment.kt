package com.computerwhizstore.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.computerwhizstore.MainActivity
import com.computerwhizstore.R
import com.computerwhizstore.adapters.*
import com.computerwhizstore.databinding.FragmentListBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.*
import com.computerwhizstore.utils.Constants
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper
import com.computerwhizstore.utils.views.DividerItemDecoration

class ListFragment : BaseFragment(), IClickListener, View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(isFrom: String): ListFragment {
            val categoriesListFragment = ListFragment()
            val bundle = Bundle()
            bundle.putString("isFrom", isFrom)
            categoriesListFragment.arguments = bundle
            return categoriesListFragment
        }
    }

    private var rootView: View? = null
    private lateinit var binding: FragmentListBinding

    private var salesReportsModelListArrayList: ArrayList<SalesReportsModel>? = null
    private var customersModelListArrayList: ArrayList<CustomersModel>? = null
    private var inventoryModelListArrayList: ArrayList<InventoryModel>? = null
    private var invoicesModelListArrayList: ArrayList<InvoiceModel>? = null
    private var packagingSlipsModelListArrayList: ArrayList<PackagingSlipsModel>? = null

    private lateinit var mainActivity: MainActivity

    private var salesListAdapter: SalesListAdapter? = null
    private var customersAdapter: CustomersAdapter? = null
    private var invoicesAdapter: InvoicesAdapter? = null
    private var packagingSlipsAdapter: PackagingSlipsAdapter? = null
    private var inventoryAdapter: InventoryAdapter? = null

    private var isFrom: String? = ""

    override fun onResume() {
        super.onResume()
        mainActivity.setTopBar(StaticUtils.getHeading(isFrom))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
//        dialog = PopUtils.SimpleProgressDialog(mainActivity)
        arguments.let {
            isFrom = it?.getString("isFrom")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        rootView = binding.root
        initComponents()
        return rootView
    }

    private fun initComponents() {
        showProgressBar()
        setUpFilter()
        when (isFrom) {
            Constants.SCREEN_SALES -> {
                binding.edtSearchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                salesReportsModelListArrayList = ArrayList()
//            salesReportsModelListArrayList?.addAll(DbHelper(mainActivity).getSalesReportsList)
                for (i in 0..10) {
                    salesReportsModelListArrayList?.add(SalesReportsModel())
                }
                setSalesListAdapter()
            }
            Constants.SCREEN_CUSTOMERS -> {
                binding.edtSearchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                customersModelListArrayList = ArrayList()
                customersModelListArrayList?.addAll(DbHelper(mainActivity).getCustomersList)
                setCustomersAdapter()
            }
            Constants.SCREEN_INVENTORY -> {
                binding.edtSearchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                inventoryModelListArrayList = ArrayList()
                inventoryModelListArrayList?.addAll(DbHelper(mainActivity).getInventoryList)
                setInventoryListAdapter()
            }
            Constants.SCREEN_INVOICES -> {
                binding.edtSearchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                invoicesModelListArrayList = ArrayList()
                for (i in 0..10) {
                    invoicesModelListArrayList?.add(InvoiceModel())
                }
                setInvoicesListAdapter()
            }
            Constants.SCREEN_PACKAGING_SLIPS -> {
                binding.edtSearchBar.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {

                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                packagingSlipsModelListArrayList = ArrayList()
                for (i in 0..10) {
                    packagingSlipsModelListArrayList?.add(PackagingSlipsModel())
                }
                setPackagingSlipsListAdapter()
            }
        }
        binding.imgAddRecord.setOnClickListener(this)
    }

    private fun setUpFilter() {
        val categories = arrayOf("Ascending", "Descending", "Date")
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilter.adapter = dataAdapter
        binding.spinnerFilter.onItemSelectedListener = this
    }

    fun setLayoutManagerAndDec() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST
            )
        )
    }

    private fun setCustomersAdapter() {
        setLayoutManagerAndDec()

        customersAdapter = CustomersAdapter(customersModelListArrayList!!, this)
        binding.recyclerView.adapter = customersAdapter

        if (customersModelListArrayList.isNullOrEmpty()) {
            showNoDataFound()
        } else {
            showList()
        }
    }

    private fun setSalesListAdapter() {
        setLayoutManagerAndDec()

        salesListAdapter = SalesListAdapter(salesReportsModelListArrayList!!, this)
        binding.recyclerView.adapter = salesListAdapter

        if (salesReportsModelListArrayList.isNullOrEmpty()) {
            showNoDataFound()
        } else {
            showList()
        }
    }

    private fun setPackagingSlipsListAdapter() {
        setLayoutManagerAndDec()

        packagingSlipsAdapter = PackagingSlipsAdapter(packagingSlipsModelListArrayList!!, this)
        binding.recyclerView.adapter = packagingSlipsAdapter

        if (packagingSlipsModelListArrayList.isNullOrEmpty()) {
            showNoDataFound()
        } else {
            showList()
        }
    }

    private fun setInvoicesListAdapter() {
        setLayoutManagerAndDec()

        invoicesAdapter = InvoicesAdapter(invoicesModelListArrayList!!, this)
        binding.recyclerView.adapter = invoicesAdapter

        if (invoicesModelListArrayList.isNullOrEmpty()) {
            showNoDataFound()
        } else {
            showList()
        }
    }

    private fun setInventoryListAdapter() {
        setLayoutManagerAndDec()

        inventoryAdapter = InventoryAdapter(inventoryModelListArrayList!!, this)
        binding.recyclerView.adapter = inventoryAdapter

        if (inventoryModelListArrayList.isNullOrEmpty()) {
            showNoDataFound()
        } else {
            showList()
        }
    }

    private fun showList() {
        binding.txtNoDataFound.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showNoDataFound() {
        binding.txtNoDataFound.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.txtNoDataFound.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }

    override fun onClick(view: View, position: Int) {
//        val selectedModel = salesReportsModelListArrayList?.get(position)
//        mainActivity.replaceFragment(QuestionsListFragment(), true)
//        val intent = Intent(mainActivity, QuestionsActivity::class.java)
//        intent.putExtra("categoryId", selectedModel?.id)
//        intent.putExtra("anganwadiId", isFrom)
//        startActivity(intent)
        mainActivity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        when (isFrom) {
            Constants.SCREEN_INVENTORY -> {
                val selectedModel = inventoryModelListArrayList?.get(position)
                mainActivity.replaceFragment(
                    AddInventoryFragment.newInstance(selectedModel!!),
                    true
                )
            }
            Constants.SCREEN_CUSTOMERS -> {
                val selectedModel = customersModelListArrayList?.get(position)
                mainActivity.replaceFragment(AddCustomerFragment.newInstance(selectedModel!!), true)
            }
        }
    }

    override fun onLongClick(view: View, position: Int) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgAddRecord -> {
                when (isFrom) {
                    Constants.SCREEN_INVENTORY -> {
                        mainActivity.replaceFragment(AddInventoryFragment(), true)
                    }
                    Constants.SCREEN_CUSTOMERS -> {
                        mainActivity.replaceFragment(AddCustomerFragment(), true)
                    }
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

}