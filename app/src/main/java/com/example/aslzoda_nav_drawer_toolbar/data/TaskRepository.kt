package com.example.aslzoda_nav_drawer_toolbar.data

class TaskRepository(private val pref: TaskPref) {

     fun allTasks(): List<Task> =
        pref.getAll()

     fun insert(task: Task) =
        pref.insert(task)

     fun update(task: Task) =
        pref.update(task)

     fun delete(task: Task) =
        pref.delete(task)

     fun done(): Int =
        pref.doneCount()

     fun getById(id: Int): Task? =
        pref.getById(id)


    fun search(q: String): List<Task> =
        pref.search(q)


    fun byPriority(p: String): List<Task> =
        pref.byPriority(p)


    fun pending(): List<Task> =
        pref.pending()


    fun doneList(): List<Task> =
        pref.done()


    fun today(): List<Task> =
        pref.today()
}
