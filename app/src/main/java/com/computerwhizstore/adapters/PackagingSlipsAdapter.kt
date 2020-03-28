package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemCustomersBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.PackagingSlipsModel
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class PackagingSlipsAdapter(
    itemsData: ArrayList<PackagingSlipsModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<PackagingSlipsAdapter.ViewHolder>() {
    private lateinit var mItemManger: ViewBinderHelper;

    private var customersArrayList: ArrayList<PackagingSlipsModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_customers, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(customersArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return customersArrayList!!.size
    }

    class ViewHolder(var binding: ItemCustomersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesModel: PackagingSlipsModel, iClickListener: IClickListener?) {
            binding.txtName.text = "${categoriesModel.packaginSlipNo}"
            binding.relBody.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}