package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class UserModel() : Parcelable {

    var userName: String? = null
    var phoneNumber: String? = null
    var password: String? = null
    var userId: String? = null

    //    admin 0, user 1
    var userType: Int? = null

    //    status 1 active 2 suspended
    var userStatus: Int? = null

    constructor(parcel: Parcel) : this() {
        userName = parcel.readString()
        phoneNumber = parcel.readString()
        password = parcel.readString()
        userId = parcel.readString()
        userType = parcel.readValue(Int::class.java.classLoader) as? Int
        userStatus = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(phoneNumber)
        parcel.writeString(password)
        parcel.writeString(userId)
        parcel.writeValue(userType)
        parcel.writeValue(userStatus)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return ((other as UserModel).userId.equals(this.userId) && other.password.equals(this.password))
//        return super.equals(other)
    }
}