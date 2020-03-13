package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemSalesReportBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.SalesReportsModel
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class SalesListAdapter(
    itemsData: ArrayList<SalesReportsModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<SalesListAdapter.ViewHolder>() {
    private lateinit var mItemManger: ViewBinderHelper;

    private var categoriesArrayList: ArrayList<SalesReportsModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_sales_report, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(categoriesArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return categoriesArrayList!!.size
    }

    class ViewHolder(var binding: ItemSalesReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesModel: SalesReportsModel, iClickListener: IClickListener?) {
            binding.txtName.text = categoriesModel.name
            binding.root.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}