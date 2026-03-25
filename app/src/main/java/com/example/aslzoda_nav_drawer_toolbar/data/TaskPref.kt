package com.example.aslzoda_nav_drawer_toolbar.data



import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class TaskPref(context: Context) {

    private val prefs =
        context.getSharedPreferences("tasks_pref", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val KEY = "tasks"

    private fun load(): MutableList<Task> {
        val json = prefs.getString(KEY, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Task>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun save(list: List<Task>) {
        prefs.edit().putString(KEY, gson.toJson(list)).apply()
    }



    fun getAll(): List<Task> =
        load().sortedBy { it.deadline }


    fun insert(task: Task) {
        val list = load()
        list.add(task)
        save(list)
    }


    fun update(task: Task) {
        val list = load()
        val index = list.indexOfFirst { it.id == task.id }
        if (index != -1) {
            list[index] = task
            save(list)
        }
    }


    fun delete(task: Task) {
        val list = load()
        list.removeAll { it.id == task.id }
        save(list)
    }


    fun doneCount(): Int =
        load().count { it.isDone }


    fun pendingCount(): Int =
        load().count { !it.isDone }


    fun getById(id: Int): Task? =
        load().find { it.id == id }


    fun search(q: String): List<Task> =
        load().filter {
            it.title.contains(q, ignoreCase = true)
        }.sortedBy { it.deadline }


    fun byPriority(p: String): List<Task> =
        load().filter { it.priority == p }


    fun pending(): List<Task> =
        load().filter { !it.isDone }


    fun done(): List<Task> =
        load().filter { it.isDone }


    fun today(): List<Task> {
        val calNow = Calendar.getInstance()
        return load().filter { task ->
            val cal = Calendar.getInstance().apply {
                timeInMillis = task.deadline
            }
            cal.get(Calendar.YEAR) == calNow.get(Calendar.YEAR) &&
                    cal.get(Calendar.DAY_OF_YEAR) == calNow.get(Calendar.DAY_OF_YEAR)
        }
    }
}
