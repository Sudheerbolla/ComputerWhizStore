package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class InventoryModel() : Parcelable {

    var productName: String? = null
    var brand: String? = null
    var quantity: Int? = null
    var categoryId: Int? = null
    var subCategoryId: Int? = null
    var inventoryId: Int? = null
    var productDescription: String? = null
    var unitPrice: Double? = null

    constructor(parcel: Parcel) : this() {
        productName = parcel.readString()
        brand = parcel.readString()
        quantity = parcel.readValue(Int::class.java.classLoader) as? Int
        categoryId = parcel.readValue(Int::class.java.classLoader) as? Int
        subCategoryId = parcel.readValue(Int::class.java.classLoader) as? Int
        inventoryId = parcel.readInt()
        productDescription = parcel.readString()
        unitPrice = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productName)
        parcel.writeString(brand)
        parcel.writeValue(quantity)
        parcel.writeValue(categoryId)
        parcel.writeValue(subCategoryId)
        parcel.writeValue(inventoryId)
        parcel.writeString(productDescription)
        parcel.writeValue(unitPrice)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<InventoryModel> {
        override fun createFromParcel(parcel: Parcel): InventoryModel {
            return InventoryModel(parcel)
        }

        override fun newArray(size: Int): Array<InventoryModel?> {
            return arrayOfNulls(size)
        }
    }

}