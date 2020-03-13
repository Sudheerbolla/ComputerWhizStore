package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class SalesReportsModel() : Parcelable {

    var salesId: Int? = null
    var name: String? = null
    var subTotal: Double? = null
    var tax1: Double? = null
    var tax2: Double? = null
    var totalAmount: Double? = null
    var discount: Double? = null
    var quantity: Int? = null
    var timeStamp: Long? = null
    var productId: Int? = null
    var addressId: Int? = null
    var customerId: Int? = null
    var salesPersonId: Int? = null

    constructor(parcel: Parcel) : this() {
        salesId = parcel.readInt()
        name = parcel.readString()
        subTotal = parcel.readValue(Double::class.java.classLoader) as? Double
        tax1 = parcel.readValue(Double::class.java.classLoader) as? Double
        tax2 = parcel.readValue(Double::class.java.classLoader) as? Double
        totalAmount = parcel.readValue(Double::class.java.classLoader) as? Double
        discount = parcel.readValue(Double::class.java.classLoader) as? Double
        quantity = parcel.readValue(Int::class.java.classLoader) as? Int
        timeStamp = parcel.readValue(Long::class.java.classLoader) as? Long
        productId = parcel.readValue(Int::class.java.classLoader) as? Int
        addressId = parcel.readValue(Int::class.java.classLoader) as? Int
        customerId = parcel.readValue(Int::class.java.classLoader) as? Int
        salesPersonId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeValue(salesId)
        dest?.writeString(name)
        dest?.writeValue(subTotal)
        dest?.writeValue(tax1)
        dest?.writeValue(tax2)
        dest?.writeValue(totalAmount)
        dest?.writeValue(discount)
        dest?.writeValue(quantity)
        dest?.writeValue(timeStamp)
        dest?.writeValue(productId)
        dest?.writeValue(addressId)
        dest?.writeValue(customerId)
        dest?.writeValue(salesPersonId)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<SalesReportsModel> {
        override fun createFromParcel(parcel: Parcel): SalesReportsModel {
            return SalesReportsModel(parcel)
        }

        override fun newArray(size: Int): Array<SalesReportsModel?> {
            return arrayOfNulls(size)
        }
    }

}