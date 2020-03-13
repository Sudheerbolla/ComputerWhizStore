package com.computerwhizstore.models

import android.os.Parcel
import android.os.Parcelable

class CategoryModel() : Parcelable {

    var categoryName: String? = null
    var categoryId: Int? = null

    constructor(parcel: Parcel) : this() {
        categoryName = parcel.readString()
        categoryId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(categoryName)
        parcel.writeValue(categoryId)
    }

    override fun describeContents(): Int {
        return hashCode()
    }

    companion object CREATOR : Parcelable.Creator<CategoryModel> {
        override fun createFromParcel(parcel: Parcel): CategoryModel {
            return CategoryModel(parcel)
        }

        override fun newArray(size: Int): Array<CategoryModel?> {
            return arrayOfNulls(size)
        }
    }


}