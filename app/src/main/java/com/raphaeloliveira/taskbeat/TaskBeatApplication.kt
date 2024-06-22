package com.raphaeloliveira.taskbeat

import android.app.Application
import androidx.room.Room
import com.raphaeloliveira.taskbeat.data.TaskBeatDataBase
import com.raphaeloliveira.taskbeat.module.categoryDatabaseModule
import com.raphaeloliveira.taskbeat.module.taskDatabaseModule
import org.koin.core.context.startKoin

class TaskBeatApplication : Application(){

    lateinit var db: TaskBeatDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            TaskBeatDataBase::class.java, "database-taskbeat"
        ).build()

        startKoin {
            modules(listOf
                (taskDatabaseModule(this@TaskBeatApplication),
                categoryDatabaseModule(this@TaskBeatApplication)))
        }
    }

    fun getDatabase(): TaskBeatDataBase {
            return db
    }
}