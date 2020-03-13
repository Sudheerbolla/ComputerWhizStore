package com.computerwhizstore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.computerwhizstore.ui.login.LoginActivity
import com.computerwhizstore.utils.AppLocalStorage

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable {
            if (TextUtils.isEmpty(
                    AppLocalStorage.getInstance(this).getString(AppLocalStorage.PREF_USER_ID, "")
                )
            ) startActivity(Intent(this, LoginActivity::class.java))
            else startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }, 2000)
    }

}
