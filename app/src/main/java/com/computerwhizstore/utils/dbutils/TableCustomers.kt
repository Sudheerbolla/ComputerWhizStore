package com.computerwhizstore.utils.dbutils

internal object TableCustomers {

    var customerId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var middleName: String? = null
    var phoneNumber: String? = null
    var emailAddress: String? = null

    val TABLE_NAME = "TableCustomers"
    var CREATE_TABLE: String

    init {

        firstName = "zFirstName"
        lastName = "zLastName"
        middleName = "zMiddleName"
        phoneNumber = "zPhoneNumber"
        emailAddress = "zEmailAddress"
        customerId = "zCustomerId"

        CREATE_TABLE =
            "create table if not exists $TABLE_NAME ( $customerId INTEGER PRIMARY KEY NOT NULL, $firstName TEXT," +
                    " $lastName TEXT, $middleName TEXT, $phoneNumber TEXT, $emailAddress TEXT, UNIQUE ($customerId) ON CONFLICT REPLACE);"

    }

}