package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemSalesReportBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.SalesReportsModel
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class SalesListAdapter(
    itemsData: ArrayList<SalesReportsModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<SalesListAdapter.ViewHolder>() {

    private var categoriesArrayList: ArrayList<SalesReportsModel>? = itemsData
    private var mItemManger: ViewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_sales_report, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        mItemManger.setOpenOnlyOne(true)
        viewHolder.bind(categoriesArrayList!![position], iClickListener, mItemManger)
    }

    override fun getItemCount(): Int {
        return categoriesArrayList!!.size
    }

    class ViewHolder(var binding: ItemSalesReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            categoriesModel: SalesReportsModel, iClickListener: IClickListener?,
            mItemManger: ViewBinderHelper
        ) {
            binding.txtName.text = "Sales Id: ${categoriesModel.salesId}"
            binding.txtPrice.text = "Amount: $ ${categoriesModel.totalAmount}"
            binding.txtDate.text = "${StaticUtils.getDateFromTimeStamp(categoriesModel.timeStamp)}"
            mItemManger.bind(binding.swipeLayout, adapterPosition.toString())
            mItemManger.closeAll()
            binding.relBody.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtPackagingSlips.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtInvoice.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}