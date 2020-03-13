package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class PackagingSlipsModel() : Parcelable {

    var packaginSlipNo: String? = null
    var orderId: String? = null
    var timeStamp: String? = null
    var customersModel: CustomersModel? = null
    var salesReportsModel: SalesReportsModel? = null

    constructor(parcel: Parcel) : this() {
        packaginSlipNo = parcel.readString()
        orderId = parcel.readString()
        timeStamp = parcel.readString()
        customersModel = parcel.readParcelable(CustomersModel::class.java.classLoader)
        salesReportsModel = parcel.readParcelable(SalesReportsModel::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(packaginSlipNo)
        parcel.writeString(orderId)
        parcel.writeString(timeStamp)
        parcel.writeParcelable(customersModel, flags)
        parcel.writeParcelable(salesReportsModel, flags)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<PackagingSlipsModel> {
        override fun createFromParcel(parcel: Parcel): PackagingSlipsModel {
            return PackagingSlipsModel(parcel)
        }

        override fun newArray(size: Int): Array<PackagingSlipsModel?> {
            return arrayOfNulls(size)
        }
    }


}