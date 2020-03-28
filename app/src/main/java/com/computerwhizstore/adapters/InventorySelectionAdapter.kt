package com.computerwhizstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.computerwhizstore.R
import com.computerwhizstore.databinding.ItemInventorySelectionBinding
import com.computerwhizstore.interfaces.IClickListener
import com.computerwhizstore.models.InventoryModel

class InventorySelectionAdapter(
    itemsData: ArrayList<InventoryModel>, private var iClickListener: IClickListener?
) : RecyclerView.Adapter<InventorySelectionAdapter.ViewHolder>() {

    private var inventoryArrayList: ArrayList<InventoryModel>? = itemsData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_inventory_selection, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(inventoryArrayList!![position], iClickListener)
    }

    override fun getItemCount(): Int {
        return inventoryArrayList!!.size
    }

    class ViewHolder(var binding: ItemInventorySelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoriesModel: InventoryModel, iClickListener: IClickListener?) {
            binding.txtProductName.text = "${categoriesModel.productName}"
            binding.txtPrice.text = "$ ${categoriesModel.unitPrice}"
            binding.txtProductDescription.text = "${categoriesModel.productDescription}"
            binding.txtSelectedQuanity.text = "${categoriesModel.selectedQuantity}"
            binding.txtQuanity.text = "Available Quantity: ${categoriesModel.quantity}"
            binding.txtCategory.text = "${categoriesModel.categoryId}"
            binding.txtSubCategory.text = "${categoriesModel.subCategoryId}"
            binding.relBody.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtMinus.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
            binding.txtPlus.setOnClickListener { v ->
                if (iClickListener != null) iClickListener.onClick(v, adapterPosition)
            }
        }

    }

}