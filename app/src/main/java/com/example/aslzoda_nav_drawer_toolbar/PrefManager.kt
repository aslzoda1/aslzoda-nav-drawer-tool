package com.example.aslzoda_nav_drawer_toolbar.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object PrefManager {

    private const val PREF_NAME = "user_pref"
    private const val KEY_REGISTERED = "registered"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_IMAGE = "image"

    private fun prefs(ctx: Context) =
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    private val _nameLiveData = MutableLiveData("")
    private val _emailLiveData = MutableLiveData("")
    private val _imageLiveData = MutableLiveData<String?>(null)





    val nameLiveData: LiveData<String> = _nameLiveData


    val emailLiveData: LiveData<String> = _emailLiveData


    val imageLiveData: LiveData<String?> = _imageLiveData


    fun setRegistered(ctx: Context, value: Boolean = true) {
        prefs(ctx).edit().putBoolean(KEY_REGISTERED, value).apply()
    }

    fun isRegistered(ctx: Context): Boolean =
        prefs(ctx).getBoolean(KEY_REGISTERED, false)


    fun saveName(ctx: Context, name: String) {
        prefs(ctx).edit().putString(KEY_NAME, name).apply()
        _nameLiveData.value = name
    }

    fun saveEmail(ctx: Context, email: String) {
        prefs(ctx).edit().putString(KEY_EMAIL, email).apply()
        _emailLiveData.value = email
    }

    fun saveImage(ctx: Context, imagePath: String?) {
        prefs(ctx).edit().putString(KEY_IMAGE, imagePath).apply()
        _imageLiveData.value = imagePath
    }


    fun getName(ctx: Context) =
        prefs(ctx).getString(KEY_NAME, "") ?: ""

    fun getEmail(ctx: Context) =
        prefs(ctx).getString(KEY_EMAIL, "") ?: ""

    fun getImage(ctx: Context) =
        prefs(ctx).getString(KEY_IMAGE, null)


    fun initLiveData(ctx: Context) {
        _nameLiveData.value = getName(ctx)
        _emailLiveData.value = getEmail(ctx)
        _imageLiveData.value = getImage(ctx)
    }
}
