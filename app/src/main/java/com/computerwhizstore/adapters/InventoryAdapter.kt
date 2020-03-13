package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemInventoryBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.utils.views.swipeutils.ViewBinderHelper

class InventoryAdapter(
    itemsData: ArrayList<InventoryModel>,
    private var iClickListener: IClickListener?
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    private lateinit var mItemManger: ViewBinderHelper;

    private var customersArrayList: ArrayList<InventoryModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_inventory, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(customersArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return customersArrayList!!.size
    }

    class ViewHolder(var binding: ItemInventoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesModel: InventoryModel, iClickListener: IClickListener?) {
            binding.txtProductName.text = "${categoriesModel.productName}"
            binding.txtPrice.text = "$ ${categoriesModel.unitPrice}"
            binding.txtProductDescription.text = "${categoriesModel.productDescription}"
            binding.txtQuanity.text = "Qty: ${categoriesModel.quantity}"
            binding.txtCategory.text = "${categoriesModel.categoryId}"
            binding.txtSubCategory.text = "${categoriesModel.subCategoryId}"
            binding.root.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}