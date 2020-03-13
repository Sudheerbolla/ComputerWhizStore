package com.computerwhizstore.utils.dbutils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.models.InventoryModel
import com.computerwhizstore.models.SalesReportsModel

class DbHelper(context: Context) {

    private val databaseHandler: DatabaseHandler

    init {
        databaseHandler = DatabaseHandler.getInstance(context)
    }

    /**
     * Add individual Records in tables
     */
    @Synchronized
    fun addSalesReport(salesReportsModel: SalesReportsModel): Long {
        databaseHandler.getWritableDatabase()
        val values = ContentValues()
        if (salesReportsModel.salesId != null) {
            values.put(TableSales.salesId, salesReportsModel.salesId)
        }
        values.put(TableSales.name, salesReportsModel.name)
        values.put(TableSales.subTotal, salesReportsModel.subTotal)
        values.put(TableSales.tax1, salesReportsModel.tax1)
        values.put(TableSales.tax2, salesReportsModel.tax2)
        values.put(TableSales.totalAmount, salesReportsModel.totalAmount)
        values.put(TableSales.discount, salesReportsModel.discount)
        values.put(TableSales.productId, salesReportsModel.productId)
        values.put(TableSales.quantity, salesReportsModel.quantity)
        values.put(TableSales.timeStamp, salesReportsModel.timeStamp)
        values.put(TableSales.salesPersonId, salesReportsModel.salesPersonId)
        values.put(TableSales.customerId, salesReportsModel.customerId)
        values.put(TableSales.addressId, salesReportsModel.addressId)
        try {
            return databaseHandler.insertData(TableSales.TABLE_NAME, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    @Synchronized
    fun addInventory(inventoryModel: InventoryModel): Long {
        databaseHandler.getWritableDatabase()
        val values = ContentValues()
        if (inventoryModel.inventoryId != null) {
            values.put(TableInventory.inventoryId, inventoryModel.inventoryId)
        }
        values.put(TableInventory.brand, inventoryModel.brand)
        values.put(TableInventory.categoryId, inventoryModel.categoryId)
        values.put(TableInventory.description, inventoryModel.productDescription)
        values.put(TableInventory.image, "")
        values.put(TableInventory.name, inventoryModel.productName)
        values.put(TableInventory.price, inventoryModel.unitPrice)
        values.put(TableInventory.quantity, inventoryModel.quantity)
        values.put(TableInventory.subCategoryId, inventoryModel.subCategoryId)
        try {
            return databaseHandler.insertData(TableInventory.TABLE_NAME, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    @Synchronized
    fun addCustomer(customersModel: CustomersModel): Long {
        databaseHandler.getWritableDatabase()
        val values = ContentValues()
        if (customersModel.customerId != null) {
            values.put(TableCustomers.customerId, customersModel.customerId)
        }
        values.put(TableCustomers.firstName, customersModel.firstName)
        values.put(TableCustomers.lastName, customersModel.lastName)
        values.put(TableCustomers.middleName, customersModel.middleName)
        values.put(TableCustomers.emailAddress, customersModel.emailAddress)
        values.put(TableCustomers.phoneNumber, customersModel.phoneNumber)
        try {
            return databaseHandler.insertData(TableCustomers.TABLE_NAME, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    @Synchronized
    fun addAddress(addressesModel: AddressesModel): Long {
        databaseHandler.getWritableDatabase()
        val values = ContentValues()
        if (addressesModel.addressId != null) {
            values.put(TableAddress.addressId, addressesModel.addressId)
        }
        values.put(TableAddress.line1, addressesModel.line1)
        values.put(TableAddress.line2, addressesModel.line2)
        values.put(TableAddress.city, addressesModel.city)
        values.put(TableAddress.customerId, addressesModel.customerId)
        values.put(TableAddress.province, addressesModel.province)
        values.put(TableAddress.zipcode, addressesModel.zipcode)
        try {
            return databaseHandler.insertData(TableAddress.TABLE_NAME, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    @Synchronized
    fun getSalesReportsModel(salesId: String): SalesReportsModel {
        val salesReportsModel = SalesReportsModel()
        val selectQuery =
            "select * FROM " + TableSales.TABLE_NAME + " WHERE " + TableSales.salesId + "='" + salesId + "'"
        databaseHandler.getReadableDatabase()
        val cursor = databaseHandler.selectData(selectQuery, true)
        if (cursor.moveToFirst()) {
            do {
                salesReportsModel.quantity =
                    cursor.getInt(cursor.getColumnIndex(TableSales.quantity))
                salesReportsModel.timeStamp =
                    cursor.getLong(cursor.getColumnIndex(TableSales.timeStamp))
                salesReportsModel.salesId = cursor.getInt(cursor.getColumnIndex(TableSales.salesId))
                salesReportsModel.addressId =
                    cursor.getInt(cursor.getColumnIndex(TableSales.addressId))
                salesReportsModel.customerId =
                    cursor.getInt(cursor.getColumnIndex(TableSales.customerId))
                salesReportsModel.productId =
                    cursor.getInt(cursor.getColumnIndex(TableSales.productId))
                salesReportsModel.salesPersonId =
                    cursor.getInt(cursor.getColumnIndex(TableSales.salesPersonId))
                salesReportsModel.name =
                    cursor.getString(cursor.getColumnIndex(TableSales.name))
                salesReportsModel.subTotal =
                    cursor.getDouble(cursor.getColumnIndex(TableSales.subTotal))
                salesReportsModel.tax1 =
                    cursor.getDouble(cursor.getColumnIndex(TableSales.tax1))
                salesReportsModel.tax2 =
                    cursor.getDouble(cursor.getColumnIndex(TableSales.tax2))
                salesReportsModel.totalAmount =
                    cursor.getDouble(cursor.getColumnIndex(TableSales.totalAmount))
                salesReportsModel.discount =
                    cursor.getDouble(cursor.getColumnIndex(TableSales.discount))
            } while (cursor.moveToNext())
        }
        if (!cursor.isClosed()) {
            cursor.close()
        }
        return salesReportsModel
    }

    /*val getSalesReportsListSize: String
        @Synchronized get() = DatabaseUtils.stringForQuery(
            databaseHandler.getReadableDatabase(),
            "SELECT COUNT(*) FROM " + TableSales.TABLE_NAME,
            null
        )*/

    /**
     * Get list of records in ascending order for each individual table
     */
    val getSalesReportsList: ArrayList<SalesReportsModel>
        @Synchronized get() {
            val arrayList = ArrayList<SalesReportsModel>()
            val selectQuery =
                "select * FROM " + TableSales.TABLE_NAME + " ORDER BY " + TableSales.salesId + " ASC"
            databaseHandler.getReadableDatabase()
            val cursor = databaseHandler.selectData(selectQuery, true)
            if (cursor.moveToFirst()) {
                do {
                    val salesReportsModel = SalesReportsModel()
                    salesReportsModel.quantity =
                        cursor.getInt(cursor.getColumnIndex(TableSales.quantity))
                    salesReportsModel.timeStamp =
                        cursor.getLong(cursor.getColumnIndex(TableSales.timeStamp))
                    salesReportsModel.salesId =
                        cursor.getInt(cursor.getColumnIndex(TableSales.salesId))
                    salesReportsModel.addressId =
                        cursor.getInt(cursor.getColumnIndex(TableSales.addressId))
                    salesReportsModel.customerId =
                        cursor.getInt(cursor.getColumnIndex(TableSales.customerId))
                    salesReportsModel.productId =
                        cursor.getInt(cursor.getColumnIndex(TableSales.productId))
                    salesReportsModel.salesPersonId =
                        cursor.getInt(cursor.getColumnIndex(TableSales.salesPersonId))
                    salesReportsModel.name =
                        cursor.getString(cursor.getColumnIndex(TableSales.name))
                    salesReportsModel.subTotal =
                        cursor.getDouble(cursor.getColumnIndex(TableSales.subTotal))
                    salesReportsModel.tax1 =
                        cursor.getDouble(cursor.getColumnIndex(TableSales.tax1))
                    salesReportsModel.tax2 =
                        cursor.getDouble(cursor.getColumnIndex(TableSales.tax2))
                    salesReportsModel.totalAmount =
                        cursor.getDouble(cursor.getColumnIndex(TableSales.totalAmount))
                    salesReportsModel.discount =
                        cursor.getDouble(cursor.getColumnIndex(TableSales.discount))
                    arrayList.add(salesReportsModel)
                } while (cursor.moveToNext())
            }
            if (!cursor.isClosed()) {
                cursor.close()
            }
            return arrayList
        }

    val getCustomersList: ArrayList<CustomersModel>
        @Synchronized get() {
            val arrayList = ArrayList<CustomersModel>()
            val selectQuery =
                "select * FROM " + TableCustomers.TABLE_NAME + " ORDER BY " + TableCustomers.customerId + " ASC"
            databaseHandler.getReadableDatabase()
            val cursor = databaseHandler.selectData(selectQuery, true)
            if (cursor.moveToFirst()) {
                do {
                    val customersModel = CustomersModel()
                    customersModel.customerId =
                        cursor.getInt(cursor.getColumnIndex(TableCustomers.customerId))
                    customersModel.firstName =
                        cursor.getString(cursor.getColumnIndex(TableCustomers.firstName))
                    customersModel.lastName =
                        cursor.getString(cursor.getColumnIndex(TableCustomers.lastName))
                    customersModel.middleName =
                        cursor.getString(cursor.getColumnIndex(TableCustomers.middleName))
                    customersModel.emailAddress =
                        cursor.getString(cursor.getColumnIndex(TableCustomers.emailAddress))
                    customersModel.phoneNumber =
                        cursor.getString(cursor.getColumnIndex(TableCustomers.phoneNumber))
                    arrayList.add(customersModel)
                } while (cursor.moveToNext())
            }
            if (!cursor.isClosed()) {
                cursor.close()
            }
            return arrayList
        }

    val getInventoryList: ArrayList<InventoryModel>
        @Synchronized get() {
            val arrayList = ArrayList<InventoryModel>()
            val selectQuery =
                "select * FROM " + TableInventory.TABLE_NAME + " ORDER BY " + TableInventory.inventoryId + " ASC"
            databaseHandler.getReadableDatabase()
            val cursor = databaseHandler.selectData(selectQuery, true)
            if (cursor.moveToFirst()) {
                do {
                    val inventoryModel = InventoryModel()
                    inventoryModel.quantity =
                        cursor.getInt(cursor.getColumnIndex(TableInventory.quantity))
                    inventoryModel.productName =
                        cursor.getString(cursor.getColumnIndex(TableInventory.name))
                    inventoryModel.brand =
                        cursor.getString(cursor.getColumnIndex(TableInventory.brand))
                    inventoryModel.categoryId =
                        cursor.getInt(cursor.getColumnIndex(TableInventory.categoryId))
                    inventoryModel.subCategoryId =
                        cursor.getInt(cursor.getColumnIndex(TableInventory.subCategoryId))
                    inventoryModel.inventoryId =
                        cursor.getInt(cursor.getColumnIndex(TableInventory.inventoryId))
                    inventoryModel.productDescription =
                        cursor.getString(cursor.getColumnIndex(TableInventory.description))
                    inventoryModel.unitPrice =
                        cursor.getDouble(cursor.getColumnIndex(TableInventory.price))
                    arrayList.add(inventoryModel)
                } while (cursor.moveToNext())
            }
            if (!cursor.isClosed()) {
                cursor.close()
            }
            return arrayList
        }

    val getAddressList: ArrayList<AddressesModel>
        @Synchronized get() {
            val arrayList = ArrayList<AddressesModel>()
            val selectQuery =
                "select * FROM " + TableAddress.TABLE_NAME + " ORDER BY " + TableAddress.addressId + " ASC"
            databaseHandler.getReadableDatabase()
            val cursor = databaseHandler.selectData(selectQuery, true)
            if (cursor.moveToFirst()) {
                do {
                    val addressesModel = AddressesModel()
                    addressesModel.addressId =
                        cursor.getInt(cursor.getColumnIndex(TableAddress.addressId))
                    addressesModel.customerId =
                        cursor.getInt(cursor.getColumnIndex(TableAddress.customerId))
                    addressesModel.city =
                        cursor.getString(cursor.getColumnIndex(TableAddress.city))
                    addressesModel.line1 =
                        cursor.getString(cursor.getColumnIndex(TableAddress.line1))
                    addressesModel.line2 =
                        cursor.getString(cursor.getColumnIndex(TableAddress.line2))
                    addressesModel.province =
                        cursor.getString(cursor.getColumnIndex(TableAddress.province))
                    addressesModel.zipcode =
                        cursor.getString(cursor.getColumnIndex(TableAddress.zipcode))
                    arrayList.add(addressesModel)
                } while (cursor.moveToNext())
            }
            if (!cursor.isClosed()) {
                cursor.close()
            }
            return arrayList
        }

    /**
     * Delete all data in tables
     */
    @Synchronized
    fun deleteAllSales(): Int {
        return databaseHandler.deleteData(TableSales.TABLE_NAME, "", null)
    }

    @Synchronized
    fun deleteAllInventory(): Int {
        return databaseHandler.deleteData(TableInventory.TABLE_NAME, "", null)
    }

    @Synchronized
    fun deleteAllCustomers(): Int {
        return databaseHandler.deleteData(TableCustomers.TABLE_NAME, "", null)
    }

    @Synchronized
    fun deleteAllAddress(): Int {
        return databaseHandler.deleteData(TableAddress.TABLE_NAME, "", null)
    }

    /**
     * Delete single item in a tables
     */
    @Synchronized
    fun deleteSalesReport(itemId: String) {
        databaseHandler.getWritableDatabase()
        databaseHandler.deleteData(
            TableSales.TABLE_NAME, TableSales.salesId + "=?",
            arrayOf(itemId)
        )
    }

    @Synchronized
    fun deleteInventory(itemId: String) {
        databaseHandler.getWritableDatabase()
        databaseHandler.deleteData(
            TableInventory.TABLE_NAME, TableInventory.inventoryId + "=?",
            arrayOf(itemId)
        )
    }

    @Synchronized
    fun deleteAddress(itemId: String) {
        databaseHandler.getWritableDatabase()
        databaseHandler.deleteData(
            TableAddress.TABLE_NAME, TableAddress.addressId + "=?",
            arrayOf(itemId)
        )
    }

    @Synchronized
    fun deleteCustomer(itemId: String) {
        databaseHandler.getWritableDatabase()
        databaseHandler.deleteData(
            TableCustomers.TABLE_NAME, TableCustomers.customerId + "=?",
            arrayOf(itemId)
        )
    }

    @Synchronized
    fun selectData(tableName: String): Cursor {
        databaseHandler.getReadableDatabase()
        return databaseHandler.selectData(tableName)
    }

    fun closeDb() {
        databaseHandler.clearDB()
        databaseHandler.close()
    }

    @Synchronized
    fun deleteAll() {
        databaseHandler.clearAllTables()
        databaseHandler.close()
    }

}