package com.example.aslzoda_nav_drawer_toolbar.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ProfilePrefs {

    private const val PREF = "profile_pref"
    private const val NAME = "name"
    private const val EMAIL = "email"
    private const val IMAGE_URI = "image_uri"


    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String> = _nameLiveData

    private val _emailLiveData = MutableLiveData<String>()
    val emailLiveData: LiveData<String> = _emailLiveData

    private val _imageLiveData = MutableLiveData<String?>()
    val imageLiveData: LiveData<String?> = _imageLiveData

    fun save(context: Context, name: String, email: String, imageUri: String?) {
        prefs(context).edit()
            .putString(NAME, name)
            .putString(EMAIL, email)
            .putString(IMAGE_URI, imageUri)
            .apply()


        _nameLiveData.postValue(name)
        _emailLiveData.postValue(email)
        _imageLiveData.postValue(imageUri)
    }

    fun name(context: Context): String =
        prefs(context).getString(NAME, "Your Name") ?: "Your Name"

    fun email(context: Context): String =
        prefs(context).getString(EMAIL, "youremail@example.com") ?: "youremail@example.com"

    fun imageUri(context: Context): String? =
        prefs(context).getString(IMAGE_URI, null)

    fun clear(context: Context) {
        prefs(context).edit().clear().apply()
        _nameLiveData.postValue("Your Name")
        _emailLiveData.postValue("youremail@example.com")
        _imageLiveData.postValue(null)
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun initLiveData(context: Context) {
        _nameLiveData.value = name(context)
        _emailLiveData.value = email(context)
        _imageLiveData.value = imageUri(context)
    }
}
