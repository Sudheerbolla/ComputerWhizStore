package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class CustomersModel() : Parcelable {

    var firstName: String? = null
    var lastName: String? = null
    var middleName: String? = null
    var phoneNumber: String? = null
    var emailAddress: String? = null
    var customerId: Int? = null

    constructor(parcel: Parcel) : this() {
        firstName = parcel.readString()
        lastName = parcel.readString()
        middleName = parcel.readString()
        phoneNumber = parcel.readString()
        emailAddress = parcel.readString()
        customerId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(middleName)
        parcel.writeString(phoneNumber)
        parcel.writeString(emailAddress)
        parcel.writeValue(customerId)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<CustomersModel> {
        override fun createFromParcel(parcel: Parcel): CustomersModel {
            return CustomersModel(parcel)
        }

        override fun newArray(size: Int): Array<CustomersModel?> {
            return arrayOfNulls(size)
        }
    }

}