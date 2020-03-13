package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class SubCategoryModel() : Parcelable {

    var subCategoryName: String? = null
    var subCategoryId: Int? = null
    var categoryId: Int? = null

    constructor(parcel: Parcel) : this() {
        subCategoryName = parcel.readString()
        subCategoryId = parcel.readValue(Int::class.java.classLoader) as? Int
        categoryId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subCategoryName)
        parcel.writeValue(subCategoryId)
        parcel.writeValue(categoryId)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<SubCategoryModel> {
        override fun createFromParcel(parcel: Parcel): SubCategoryModel {
            return SubCategoryModel(parcel)
        }

        override fun newArray(size: Int): Array<SubCategoryModel?> {
            return arrayOfNulls(size)
        }
    }

}