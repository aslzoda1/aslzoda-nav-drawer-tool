package com.example.aslzoda_nav_drawer_toolbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
class ProfileViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val imageUri = MutableLiveData<String?>()
}