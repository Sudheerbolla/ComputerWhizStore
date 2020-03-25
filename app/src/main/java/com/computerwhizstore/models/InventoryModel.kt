package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class InventoryModel() : Parcelable {

    var productName: String? = ""
    var brand: String? = ""
    var quantity: Int? = 0
    var selectedQuantity: Int? = 0
    var categoryId: Int? = 0
    var subCategoryId: Int? = 0
    var inventoryId: Int? = 0
    var paymentStatus: Int? = 0
    var productDescription: String? = ""
    var unitPrice: Double? = 0.0

    constructor(parcel: Parcel) : this() {
        productName = parcel.readString()
        brand = parcel.readString()
        quantity = parcel.readValue(Int::class.java.classLoader) as? Int
        categoryId = parcel.readValue(Int::class.java.classLoader) as? Int
        subCategoryId = parcel.readValue(Int::class.java.classLoader) as? Int
        inventoryId = parcel.readInt()
        selectedQuantity = parcel.readInt()
        paymentStatus = parcel.readInt()
        productDescription = parcel.readString()
        unitPrice = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productName)
        parcel.writeString(brand)
        parcel.writeValue(quantity)
        parcel.writeValue(paymentStatus)
        parcel.writeValue(selectedQuantity)
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