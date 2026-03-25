package com.example.aslzoda_nav_drawer_toolbar.ui.about

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.ui.home.HomeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<FloatingActionButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack(
                null,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}
