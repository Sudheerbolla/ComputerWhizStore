package com.computerwhizstore.utils.dbutils

internal object TableSales {

    var salesId: String? = null
    var name: String? = null
    var subTotal: String? = null
    var tax1: String? = null
    var tax2: String? = null
    var totalAmount: String? = null
    var discount: String? = null
    var productId: String? = null
    var quantity: String? = null
    var timeStamp: String? = null
    var salesPersonId: String? = null
    var customerId: String? = null
    var addressId: String? = null

    val TABLE_NAME = "TableSales"
    var CREATE_TABLE: String

    init {

        name = "zName"
        salesId = "zOrderId"
        subTotal = "zSubTotal"
        tax1 = "zTax1"
        tax2 = "zTax2"
        totalAmount = "zTotalAmount"
        discount = "zDiscount"
        productId = "zProductId"
        quantity = "zQuantity"
        timeStamp = "zTimeStamp"
        salesPersonId = "zSalesPersonId"
        customerId = "zCustomerId"
        addressId = "zSalesPersonId"

        CREATE_TABLE =
            "create table if not exists $TABLE_NAME ( $salesId INTEGER PRIMARY KEY NOT NULL, $name" +
                    " TEXT, $timeStamp BLOB, $productId INTEGER, $customerId INTEGER, $addressId INTEGER, $subTotal REAL, $tax1 REAL, $tax2 REAL, $totalAmount REAL, $discount REAL, $quantity INTEGER, UNIQUE ($salesId) ON CONFLICT REPLACE);"

    }

}