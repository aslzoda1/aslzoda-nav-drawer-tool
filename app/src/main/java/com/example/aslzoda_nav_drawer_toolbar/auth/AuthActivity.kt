package com.example.aslzoda_nav_drawer_toolbar.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aslzoda_nav_drawer_toolbar.MainActivity
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.utils.PrefManager


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // agar allaqachon ro‘yxatdan o‘tgan bo‘lsa → Main
        if (PrefManager.isRegistered(this)) {
            openMain()
            return
        }

        // birinchi marta → Welcome
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, WelcomeFragment())
            .commit()
    }

    fun openRegister() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
