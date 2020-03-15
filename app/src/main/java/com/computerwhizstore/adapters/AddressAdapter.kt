package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemAddressBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class AddressAdapter(
    itemsData: ArrayList<AddressesModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private lateinit var mItemManger: ViewBinderHelper;

    private var customersArrayList: ArrayList<AddressesModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_address, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(customersArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return customersArrayList!!.size
    }

    class ViewHolder(var binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(addressesModel: AddressesModel, iClickListener: IClickListener?) {
            binding.txtRestOfAddress.text =
                "${addressesModel.city}, ${addressesModel.province}, ${addressesModel.zipcode} "
            binding.txtLine1.text = "${addressesModel.line1}"
            binding.txtLine2.text = "${addressesModel.line2}"
            binding.root.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}