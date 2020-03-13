package com.computerwhizstore.utils

class Constants {

    companion object {
        val ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L // 1 Day

        //        val DELAY_TIME = 2000.toLong()
        val QUARTER_SECOND_DELAY = 250.toLong()
        val HALF_SECOND_DELAY = 500.toLong()
        val ONE_SECOND_DELAY = 1000.toLong()
        val BACK_PRESSED_TIME = 2000.toLong()

        val DELAY_TIME = QUARTER_SECOND_DELAY

        val LOCATION_REQUEST_CODE = 1

        val TWENTY_FIVE = 25
        val THIRTY = 30
        val FORTY = 40
        val FIFTY = 50

        val PERMISSION_LOCATION_SERVICES = 1001
        val CONTENT_TYPE = "Content-Type"

        val CUSTOMER_ID = "customer_id"

        val SCREEN_SALES = "SCREEN_SALES"
        val SCREEN_CUSTOMERS = "SCREEN_CUSTOMERS"
        val SCREEN_PACKAGING_SLIPS = "SCREEN_PACKAGING_SLIPS"
        val SCREEN_INVOICES = "SCREEN_INVOICES"
        val SCREEN_INVENTORY = "SCREEN_INVENTORY"

    }

}