package com.example.aslzoda_nav_drawer_toolbar.data

import android.content.Context

class TaskStore private constructor(context: Context) {

    val tasks: TaskPref = TaskPref(context)

    companion object {
        @Volatile
        private var INSTANCE: TaskStore? = null

        fun get(context: Context): TaskStore {
            return INSTANCE ?: synchronized(this) {
                val instance = TaskStore(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
