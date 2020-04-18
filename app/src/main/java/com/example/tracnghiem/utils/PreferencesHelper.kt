package com.example.tracnghiem.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.*

class PreferencesHelper constructor(context: Context) {
    private val sharedPref: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPref = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun clear() {
        sharedPref.edit().clear().apply()
    }

    fun getString(key: String): String = sharedPref.getString(key, "") ?: ""

    fun saveInt(key: String,value :Int){
        val  editor = sharedPref.edit()
        editor.putInt(key,value)
        editor.apply()
    }


    fun getDouble(key: String): Double {
        if (getLong(key) == 0L) {
            return -1.0
        }
        return java.lang.Double.longBitsToDouble(getLong(key))
    }

    fun saveDouble(key: String, value: Double) {
        saveLong(key, java.lang.Double.doubleToRawLongBits(value))
    }

    fun saveLong(key: String, value: Long) {
        val editor = sharedPref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String): Long = sharedPref.getLong(key, 0)

    fun getInt(key: String): Int = sharedPref.getInt(key, 0)

    fun getBoolean(key: String): Boolean = sharedPref.getBoolean(key, false)

    fun saveBoolean(key: String, value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringSet(key: String) = sharedPref.getStringSet(key, TreeSet<String>())

    fun saveStringSet(key: String, value: Set<String>) {
        val editor = sharedPref.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    companion object {
        const val PREF_NAME = "Name_pref"

    }
}