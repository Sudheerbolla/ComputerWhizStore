package com.computerwhizstore

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.computerwhizstore.databinding.ActivityMainBinding
import com.computerwhizstore.fragments.DashboardFragment
import com.computerwhizstore.models.UserModel
import com.computerwhizstore.utils.AppLocalStorage
import com.computerwhizstore.utils.PopUtils
import com.computerwhizstore.utils.StaticUtils
import com.computerwhizstore.utils.dbutils.DbHelper

class MainActivity : BaseActivity(), View.OnClickListener {
    companion object {
        lateinit var userModel: UserModel
    }

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        userModel = StaticUtils.getUserModel(
            AppLocalStorage.getInstance(this).getString(AppLocalStorage.PREF_USER_ID, "")!!
        )!!
        replaceFragment(DashboardFragment(), false)
        setListeners()
    }

    private fun setListeners() {
        activityMainBinding.imgBack.setOnClickListener(this)
        activityMainBinding.txtLogout.setOnClickListener(this)
    }

    public fun setTopBar(heading: String?) {
        if (heading.equals(getString(R.string.app_name), true)) {
            activityMainBinding.imgBack.visibility = View.INVISIBLE
        } else {
            activityMainBinding.imgBack.visibility = View.VISIBLE
        }
        activityMainBinding.txtHeading.text = heading
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgBack -> {
                onBackPressed()
            }
            R.id.txtLogout -> {
                PopUtils.simpleDialog(this, "Are you sue you want to Logout?", "Logout",
                    DialogInterface.OnClickListener { dialog, which ->
                        run {
                            AppLocalStorage.getInstance(this).clear()
                            DbHelper(this).deleteAll()
                            startActivity(Intent(this, SplashActivity::class.java))
                            finishAffinity()
                        }
                    })
            }
        }
    }

}
