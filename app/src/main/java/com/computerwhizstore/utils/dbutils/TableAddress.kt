package com.computerwhizstore.utils.dbutils

internal object TableAddress {

    var addressId: String? = null
    var line1: String? = null
    var line2: String? = null
    var city: String? = null
    var province: String? = null
    var zipcode: String? = null
    var customerId: String? = null

    val TABLE_NAME = "TableAddress"
    var CREATE_TABLE: String

    init {

        line1 = "zLine1"
        line2 = "zLine2"
        city = "zCity"
        province = "zprovince"
        zipcode = "zZipcode"
        addressId = "zAddressId"
        customerId = "zCustomerId"

        CREATE_TABLE =
            "create table if not exists $TABLE_NAME ( $addressId INTEGER PRIMARY KEY NOT NULL, $line1" +
                    " TEXT, $customerId INTEGER, $line2 TEXT, $city TEXT, $province TEXT, $zipcode TEXT, UNIQUE ($addressId) ON CONFLICT REPLACE);"

    }

}