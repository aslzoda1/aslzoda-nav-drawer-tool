package com.example.aslzoda_nav_drawer_toolbar.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.ui.home.HomeFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var prefs: android.content.SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val switchTheme = view.findViewById<SwitchCompat>(R.id.switchTheme)
        val switchNotification = view.findViewById<SwitchCompat>(R.id.switchNotification)
        val spinnerSort = view.findViewById<Spinner>(R.id.spinnerSort)
        val btnClear = view.findViewById<Button>(R.id.btnClearAll)



        val btnBack = view.findViewById<FloatingActionButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        switchTheme.isChecked = prefs.getBoolean("darkMode", false)
        switchTheme.setOnCheckedChangeListener { _, checked ->

            prefs.edit().putBoolean("darkMode", checked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (checked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        switchNotification.isChecked = prefs.getBoolean("notifications", true)
        switchNotification.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean("notifications", checked).apply()
        }

        val sortOptions = arrayOf("Deadline", "Priority", "Title")

        spinnerSort.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            sortOptions
        )

        val currentSort = prefs.getString("sortBy", "Deadline") ?: "Deadline"
        spinnerSort.setSelection(sortOptions.indexOf(currentSort))

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                prefs.edit().putString("sortBy", sortOptions[position]).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnClear.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Clear all tasks?")
                .setMessage("This will permanently delete all saved tasks.")
                .setPositiveButton("Clear") { _, _ ->
                    clearTasks()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun clearTasks() {

        requireContext()
            .getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        Toast.makeText(requireContext(), "All tasks cleared", Toast.LENGTH_SHORT).show()
    }
}
