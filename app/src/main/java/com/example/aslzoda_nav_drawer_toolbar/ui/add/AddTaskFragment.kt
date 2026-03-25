package com.example.aslzoda_nav_drawer_toolbar.ui.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aslzoda_nav_drawer_toolbar.R
import com.example.aslzoda_nav_drawer_toolbar.data.Task
import com.example.aslzoda_nav_drawer_toolbar.ui.home.HomeFragment
import com.example.aslzoda_nav_drawer_toolbar.viewmodel.TaskViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTaskFragment : Fragment(R.layout.fragment_add) {

    private var deadlineMillis: Long = System.currentTimeMillis()
    private var editId: Int = -1

    companion object {
        fun edit(id: Int): AddTaskFragment {
            val f = AddTaskFragment()
            f.arguments = Bundle().apply {
                putInt("taskId", id)
            }
            return f
        }
    }

    override fun onViewCreated(v: View, s: Bundle?) {

        val vm = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val title = v.findViewById<TextInputEditText>(R.id.titleInput)
        val desc = v.findViewById<TextInputEditText>(R.id.descInput)
        val dateBtn = v.findViewById<Button>(R.id.dateBtn)
        val save = v.findViewById<MaterialButton>(R.id.saveBtn)

        val btnBack = v.findViewById<FloatingActionButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val spinner = v.findViewById<Spinner>(R.id.prioritySpinner)

        spinner.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_list,
            android.R.layout.simple_spinner_dropdown_item
        )

        editId = arguments?.getInt("taskId", -1) ?: -1

        if (editId != -1) {
            val task = vm.getTask(editId)
            if (task != null) {
                title.setText(task.title)
                desc.setText(task.description)
                deadlineMillis = task.deadline

                val sdf = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                )
                dateBtn.text = sdf.format(Date(task.deadline))

                val pos = resources
                    .getStringArray(R.array.priority_list)
                    .indexOf(task.priority)
                spinner.setSelection(pos)

                save.text = "Update Task"
            }
        }

        dateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    c.set(y, m, d)
                    deadlineMillis = c.timeInMillis
                    dateBtn.text = "$d/${m + 1}/$y"
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        save.setOnClickListener {

            val t = title.text.toString().trim()
            val d = desc.text.toString().trim()
            val p = spinner.selectedItem.toString()

            if (t.isEmpty()) {
                title.error = "Required"
                return@setOnClickListener
            }

            if (editId == -1) {
                vm.add(
                    Task(
                        id = System.currentTimeMillis().toInt(),
                        title = t,
                        description = d,
                        deadline = deadlineMillis,
                        priority = p
                    )
                )
            } else {
                vm.update(
                    Task(
                        id = editId,
                        title = t,
                        description = d,
                        deadline = deadlineMillis,
                        priority = p,
                        isDone = false
                    )
                )
            }

            parentFragmentManager.popBackStack()
        }
    }
}
