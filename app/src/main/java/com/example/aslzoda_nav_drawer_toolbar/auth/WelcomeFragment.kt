package com.example.aslzoda_nav_drawer_toolbar.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.aslzoda_nav_drawer_toolbar.R

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.btnStart).setOnClickListener {
            (activity as AuthActivity).openRegister()
        }
    }
}
