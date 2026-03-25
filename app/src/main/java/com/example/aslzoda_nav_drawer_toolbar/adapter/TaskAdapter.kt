package com.example.aslzoda_nav_drawer_toolbar.adapter

import com.example.aslzoda_nav_drawer_toolbar.data.Task
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.aslzoda_nav_drawer_toolbar.databinding.TaskItemBinding
import android.view.LayoutInflater
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.aslzoda_nav_drawer_toolbar.R
import java.util.Date
class TaskAdapter(
    val onCheck:(Task)->Unit,
    val onDelete:(Task)->Unit,
    val onEdit:(Task)->Unit
) : RecyclerView.Adapter<TaskAdapter.VH>() {

    private var list = listOf<Task>()

    fun submit(l: List<Task>) {
        list = l
        notifyDataSetChanged()
    }

    inner class VH(val b: TaskItemBinding)
        : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(p: ViewGroup, t: Int): VH {
        return VH(TaskItemBinding.inflate(
            LayoutInflater.from(p.context), p, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(h: VH, i: Int) {
        val task = list[i]

        h.b.title.text = task.title
        h.b.desc.text = task.description

        // deadline format
        val sdf = SimpleDateFormat("dd MMM yyyy",
            Locale.getDefault())
        h.b.deadline.text =
            "Deadline: ${sdf.format(Date(task.deadline))}"

        // priority badge
        h.b.priorityBadge.text = task.priority
        val bg = when(task.priority) {
            "HIGH" -> R.drawable.badge_high
            "MED" -> R.drawable.badge_med
            else -> R.drawable.badge_low
        }
        h.b.priorityBadge.setBackgroundResource(bg)

        h.b.check.isChecked = task.isDone
        h.b.title.paint.isStrikeThruText = task.isDone

        h.b.check.setOnClickListener {
            onCheck(task.copy(isDone = !task.isDone))
        }

        h.b.delete.setOnClickListener {
            onDelete(task)
        }

        h.b.edit.setOnClickListener {
            onEdit(task)
        }
        h.b.edit.setOnClickListener {
            onEdit(task)
        }
    }
}
