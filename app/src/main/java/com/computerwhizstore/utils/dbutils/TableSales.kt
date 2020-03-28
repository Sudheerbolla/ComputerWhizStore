package com.computerwhizstore.utils.dbutils

internal object TableSales {

    var salesId: String? = null
    var name: String? = null
    var subTotal: String? = null
    var tax1: String? = null
    var totalAmount: String? = null
    var discount: String? = null
    var productId: String? = null
    var quantity: String? = null
    var timeStamp: String? = null
    var salesPersonId: String? = null
    var customerId: String? = null
    var addressId: String? = null
    var productObject: String? = null

    val TABLE_NAME = "TableSales"
    var CREATE_TABLE: String

    init {

        name = "zName"
        salesId = "zOrderId"
        subTotal = "zSubTotal"
        tax1 = "zTax1"
        totalAmount = "zTotalAmount"
        discount = "zDiscount"
        productId = "zProductId"
        quantity = "zQuantity"
        timeStamp = "zTimeStamp"
        salesPersonId = "zSalesPersonId"
        customerId = "zCustomerId"
        addressId = "zAddressId"
        productObject = "zProductObject"

        CREATE_TABLE =
            "create table if not exists $TABLE_NAME ( $salesId INTEGER PRIMARY KEY NOT NULL, $name" +
                    " TEXT, $timeStamp BLOB, $productId TEXT, $productObject TEXT, $salesPersonId TEXT, $customerId INTEGER, $addressId INTEGER, $subTotal REAL, $tax1 REAL, $totalAmount REAL, $discount REAL, $quantity INTEGER, UNIQUE ($salesId) ON CONFLICT REPLACE);"

    }

}