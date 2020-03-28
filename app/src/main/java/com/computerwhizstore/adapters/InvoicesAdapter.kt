package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemInvoicesBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.InvoiceModel
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class InvoicesAdapter(
    itemsData: ArrayList<InvoiceModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<InvoicesAdapter.ViewHolder>() {
    private lateinit var mItemManger: ViewBinderHelper;

    private var customersArrayList: ArrayList<InvoiceModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_invoices, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(customersArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return customersArrayList!!.size
    }

    class ViewHolder(var binding: ItemInvoicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesModel: InvoiceModel, iClickListener: IClickListener?) {
//            binding.txtName.text =
//                "${categoriesModel.invoiceNo}"
            binding.relBody.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}