package com.computerwhizstore.utils.dbutils

internal object TableInventory {

    var inventoryId: String? = null
    var categoryId: String? = null
    var subCategoryId: String? = null
    var quantity: String? = null
    var price: String? = null
    var image: String? = null
    var name: String? = null
    var description: String? = null
    var brand: String? = null

    val TABLE_NAME = "TableInventory"
    var CREATE_TABLE: String

    init {

        inventoryId = "zINVENTORYID"
        categoryId = "zCATEGORYID"
        subCategoryId = "zSUBCATEGORYID"
        quantity = "zQUANTITY"
        price = "zPRICE"
        image = "zIMAGE"
        description = "zDESCRIPTION"
        brand = "zBRAND"
        name = "zNAME"

        CREATE_TABLE =
            "create table if not exists $TABLE_NAME ( $inventoryId INTEGER PRIMARY KEY NOT NULL, $name" +
                    " TEXT, $categoryId INTEGER, $subCategoryId INTEGER, $quantity INTEGER, $price REAL, $description TEXT, $image TEXT, $brand TEXT, UNIQUE ($inventoryId) ON CONFLICT REPLACE);"

    }

}