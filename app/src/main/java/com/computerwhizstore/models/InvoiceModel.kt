package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class InvoiceModel() : Parcelable {

    var invoiceNo: String? = null
    var orderId: String? = null
    var timeStamp: String? = null
    var price: Double? = null
    var customersModel: CustomersModel? = null
    var salesReportsModel: SalesReportsModel? = null

    constructor(parcel: Parcel) : this() {
        invoiceNo = parcel.readString()
        orderId = parcel.readString()
        timeStamp = parcel.readString()
        price = parcel.readDouble()
        customersModel = parcel.readParcelable(CustomersModel::class.java.classLoader)
        salesReportsModel = parcel.readParcelable(SalesReportsModel::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(invoiceNo)
        parcel.writeString(orderId)
        parcel.writeString(timeStamp)
        parcel.writeDouble(price!!)
        parcel.writeParcelable(customersModel, flags)
        parcel.writeParcelable(salesReportsModel, flags)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<InvoiceModel> {
        override fun createFromParcel(parcel: Parcel): InvoiceModel {
            return InvoiceModel(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceModel?> {
            return arrayOfNulls(size)
        }
    }

}