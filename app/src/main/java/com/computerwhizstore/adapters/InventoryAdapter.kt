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

    private var mItemManger: ViewBinderHelper = ViewBinderHelper()

    private var customersArrayList: ArrayList<InventoryModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_inventory, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        mItemManger.setOpenOnlyOne(true)
        viewHolder.bind(customersArrayList!![position], iClickListener, mItemManger)
    }

    override fun getItemCount(): Int {
        return customersArrayList!!.size
    }

    class ViewHolder(var binding: ItemInventoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            categoriesModel: InventoryModel,
            iClickListener: IClickListener?,
            mItemManger: ViewBinderHelper
        ) {
            mItemManger.bind(binding.swipeLayout, adapterPosition.toString())
            mItemManger.closeAll()
            binding.txtProductName.text = "${categoriesModel.productName}"
            binding.txtPrice.text = "$ ${categoriesModel.unitPrice}"
            binding.txtProductDescription.text = "${categoriesModel.productDescription}"
            binding.txtQuanity.text = "Available Quantity: ${categoriesModel.quantity}"
            binding.txtCategory.text = "${categoriesModel.categoryId}"
            binding.txtSubCategory.text = "${categoriesModel.subCategoryId}"
            binding.relBody.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtDelete.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtEdit.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}