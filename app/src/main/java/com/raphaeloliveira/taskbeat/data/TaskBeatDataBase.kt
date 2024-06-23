package com.raphaeloliveira.taskbeat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raphaeloliveira.taskbeat.domain.CategoryEntity
import com.raphaeloliveira.taskbeat.domain.TaskEntity

@Database(entities = [CategoryEntity::class, TaskEntity::class], version = 5)
abstract class TaskBeatDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao

}