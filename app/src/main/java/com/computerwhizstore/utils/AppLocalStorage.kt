package com.computerwhizstore.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.computerwhizstore.BuildConfig

class AppLocalStorage private constructor(mContext: Context) {

    private val sh: SharedPreferences
    private val edit: SharedPreferences.Editor

    init {
        sh = mContext.getSharedPreferences(PREFRENCE_NAME, Activity.MODE_PRIVATE)
        edit = sh.edit()
    }

    fun clear() {
        sh.edit().clear().apply()
    }

    fun setString(key: String, value: String) {
        edit.putString(key, value).commit()
    }

    fun getString(key: String): String? {
        return sh.getString(key, "")
    }

    fun getString(key: String, defaultValue: String): String? {
        return sh.getString(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        edit.putInt(key, value).commit()
    }

    fun getInt(key: String): Int {
        return sh.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sh.getInt(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        edit.putBoolean(key, value).commit()
    }

    fun getBoolean(key: String): Boolean {
        return sh.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sh.getBoolean(key, defaultValue)
    }

    companion object {

        val PREFRENCE_NAME = BuildConfig.APPLICATION_ID

        val PREF_SCREEN_WIDTH = "screen_width"
        val PREF_SCREEN_HEIGHT = "screen_height"

        val PREF_USER_ID = "email_id"
        val PREF_IS_FIRST_TIME_LAUNCH = "is_first_time_launch"
        val PREF_PASSWORD = "password"
        val PREF_USER_TYPE = "user_type"

        private var instance: AppLocalStorage? = null

        @Synchronized
        fun getInstance(mContext: Context): AppLocalStorage {
            if (instance == null) {
                instance = AppLocalStorage(mContext)
            }
            return instance as AppLocalStorage
        }

    }

}