package com.raphaeloliveira.taskbeat

import android.app.Application
import androidx.room.Room
import com.raphaeloliveira.taskbeat.data.TaskBeatDataBase

class TaskBeatApplication : Application(){

    lateinit var db: TaskBeatDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            TaskBeatDataBase::class.java, "database-taskbeat"
        ).build()
    }

    fun getDatabase(): TaskBeatDataBase {
            return db
    }

}