package com.raphaeloliveira.taskbeat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raphaeloliveira.taskbeat.data.CategoryDao
import com.raphaeloliveira.taskbeat.data.CategoryEntity
import com.raphaeloliveira.taskbeat.data.TaskDao
import com.raphaeloliveira.taskbeat.data.TaskEntity

@Database(entities = [CategoryEntity::class, TaskEntity::class], version = 5)
abstract class TaskBeatDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao

}