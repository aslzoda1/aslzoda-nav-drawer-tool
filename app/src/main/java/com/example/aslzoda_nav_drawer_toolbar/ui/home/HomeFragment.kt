package com.example.aslzoda_nav_drawer_toolbar.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.adapter.TaskAdapter
import com.example.aslzoda_nav_drawer_toolbar.viewmodel.TaskViewModel
import com.example.aslzoda_nav_drawer_toolbar.ui.add.AddTaskFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var vm: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onViewCreated(v: View, s: Bundle?) {

        vm = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        adapter = TaskAdapter(
            onCheck = { vm.update(it) },
            onDelete = { vm.delete(it) },
            onEdit = { task ->
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        AddTaskFragment.edit(task.id)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        )

        val rv = v.findViewById<RecyclerView>(R.id.recycler)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        vm.tasks.observe(viewLifecycleOwner) {
            adapter.submit(it)
        }

        v.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AddTaskFragment())
                    .addToBackStack(null)
                    .commit()
            }

        val sp = v.findViewById<Spinner>(R.id.filterSpinner)
        sp.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_list,
            android.R.layout.simple_spinner_dropdown_item
        )

        sp.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p: AdapterView<*>,
                    v: View?,
                    i: Int,
                    l: Long
                ) {
                    when (i) {
                        0 -> vm.showAll()
                        1 -> vm.today()
                        2 -> vm.priority("HIGH")
                        3 -> vm.pending()
                        4 -> vm.doneList()
                    }
                }

                override fun onNothingSelected(p: AdapterView<*>) {}
            }

    }

    fun search(query: String) {
        vm.search(query)
    }
}
