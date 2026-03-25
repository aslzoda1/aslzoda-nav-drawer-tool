package com.example.aslzoda_nav_drawer_toolbar.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.aslzoda_nav_drawer_toolbar.data.Task
import com.example.aslzoda_nav_drawer_toolbar.data.TaskRepository
import com.example.aslzoda_nav_drawer_toolbar.data.TaskStore

class TaskViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: TaskRepository

    private val _statsLive = MutableLiveData<Pair<Int, Int>>()
    val statsLive: LiveData<Pair<Int, Int>> get() = _statsLive

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        repo = TaskRepository(
            TaskStore.get(app).tasks
        )
        loadAll()
    }


    fun loadAll() {
        _tasks.value = repo.allTasks()
        updateStats()
    }

    fun add(task: Task) {
        repo.insert(task)
        loadAll()
    }

    fun update(task: Task) {
        repo.update(task)
        loadAll()
    }

    fun delete(task: Task) {
        repo.delete(task)
        loadAll()
    }


    fun showAll() {
        _tasks.value = repo.allTasks()
        updateStats()
    }

    fun search(q: String) {
        _tasks.value = repo.search(q)
        updateStats()
    }

    fun priority(p: String) {
        _tasks.value = repo.byPriority(p)
        updateStats()
    }

    fun pending() {
        _tasks.value = repo.pending()
        updateStats()
    }

    fun doneList() {
        _tasks.value = repo.doneList()
        updateStats()
    }

    fun today() {
        _tasks.value = repo.today()
        updateStats()
    }


    fun getTask(id: Int): Task? {
        return repo.getById(id)
    }


    private fun updateStats() {
        val list = _tasks.value ?: emptyList()
        val doneCount = list.count { it.isDone }
        val pendingCount = list.count { !it.isDone }
        _statsLive.value = Pair(doneCount, pendingCount)
    }
}
