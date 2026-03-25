package com.example.aslzoda_nav_drawer_toolbar

import androidx.lifecycle.MutableLiveData

object ProfileLiveData {
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val image = MutableLiveData<String?>()
}