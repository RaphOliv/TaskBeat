package com.raphaeloliveira.taskbeat.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class, TaskEntity::class], version = 5)
abstract class TaskBeatDataBase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao

}