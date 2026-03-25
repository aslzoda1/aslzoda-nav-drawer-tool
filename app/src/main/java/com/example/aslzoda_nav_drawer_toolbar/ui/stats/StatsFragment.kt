package com.example.aslzoda_nav_drawer_toolbar.ui.stats

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.viewmodel.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.aslzoda_nav_drawer_toolbar.ui.home.HomeFragment

class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var vm: TaskViewModel
    private lateinit var statsText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        statsText = view.findViewById(R.id.statsText)
        progressBar = view.findViewById(R.id.progressBar)

        vm.statsLive.observe(viewLifecycleOwner) { (done, pending) ->
            statsText.text = "Done: $done\nPending: $pending"
            val total = done + pending
            val progress = if (total == 0) 0 else (done * 100 / total)
            progressBar.progress = progress
        }
        val btnBack = view.findViewById<FloatingActionButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
