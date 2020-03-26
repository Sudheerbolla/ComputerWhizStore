package com.computerwhizstore.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.R
import com.computerwhizstore.databinding.LayoutPackagingSlipBinding
import com.computerwhizstore.models.AddressesModel
import com.computerwhizstore.models.CustomersModel
import com.computerwhizstore.models.SalesReportsModel

class PopUtils {
    companion object {
/*
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.layout_progress, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }

        @SuppressLint("InflateParams")
        fun SimpleProgressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.layout_progress_simple, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }*/

        fun deleteProductDialog(
            context: Context,
            message: String,
            onClickListener: DialogInterface.OnClickListener
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.app_name))
            builder.setMessage(message)
            builder.setPositiveButton("Delete") { dialog, which ->
                dialog.dismiss()
                onClickListener.onClick(dialog, which)
            }
            builder.setNeutralButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(true)
            dialog.show()
        }

        fun simpleDialog(
            context: Context,
            message: String,
            positiveButtonText: String,
            onClickListener: DialogInterface.OnClickListener
        ) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.app_name))
            builder.setMessage(message)
            builder.setPositiveButton(positiveButtonText) { dialog, which ->
                dialog.dismiss()
                onClickListener.onClick(dialog, which)
            }
            builder.setNeutralButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(true)
            dialog.show()
        }

        @SuppressLint("InflateParams")
        fun showPackagingSlip(
            context: Context,
            salesReportsModel: SalesReportsModel,
            customerModel: CustomersModel,
            addressesModel: AddressesModel
        ): Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            val packagingSlipBinding: LayoutPackagingSlipBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.layout_packaging_slip, null, false
            )
            dialog.setContentView(packagingSlipBinding.root)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            packagingSlipBinding.txtClose.setOnClickListener({
                dialog.dismiss()
            })
            packagingSlipBinding.txtPrint.setOnClickListener({
                StaticUtils.showSimpleToast(context, "Printed Packaging Slip Successfully")
                dialog.dismiss()
            })

            packagingSlipBinding.txtCustomerDetails.text =
                "${customerModel.firstName} ${customerModel.middleName} ${customerModel.lastName} \n${customerModel.emailAddress} \n${customerModel.phoneNumber} "
            packagingSlipBinding.txtAddress.text =
                "${addressesModel.line1} \n${addressesModel.line2} \n${addressesModel.city}, ${addressesModel.province}, ${addressesModel.zipcode}"
            packagingSlipBinding.txtTotalQuantity.text =
                "${context.getString(R.string.total_qty)} ${salesReportsModel.quantity}"
            packagingSlipBinding.txtTotalPrice.text =
                "${context.getString(R.string.total_price)} $ ${salesReportsModel.totalAmount}"

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }

    }

}