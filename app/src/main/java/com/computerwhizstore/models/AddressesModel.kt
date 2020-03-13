package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class AddressesModel() : Parcelable {

    var addressId: Int? = null
    var line1: String? = null
    var line2: String? = null
    var city: String? = null
    var province: String? = null
    var zipcode: String? = null
    var customerId: Int? = null

    constructor(parcel: Parcel) : this() {
        addressId = parcel.readValue(Int::class.java.classLoader) as? Int
        line1 = parcel.readString()
        line2 = parcel.readString()
        city = parcel.readString()
        province = parcel.readString()
        zipcode = parcel.readString()
        customerId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(addressId)
        parcel.writeString(line1)
        parcel.writeString(line2)
        parcel.writeString(city)
        parcel.writeString(province)
        parcel.writeString(zipcode)
        parcel.writeValue(customerId)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<AddressesModel> {
        override fun createFromParcel(parcel: Parcel): AddressesModel {
            return AddressesModel(parcel)
        }

        override fun newArray(size: Int): Array<AddressesModel?> {
            return arrayOfNulls(size)
        }
    }

}