package com.raphaeloliveira.taskbeat.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raphaeloliveira.taskbeat.domain.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskentity")
    fun getAll(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(taskEntity: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskEntity: TaskEntity)

    @Delete
    fun delete(taskEntity: TaskEntity)

    @Query("Select * FROM taskentity where category is :categoryName" )
    fun getAllByCategoryName(categoryName: String): List<TaskEntity>

    @Delete
    fun deleteAll(taskEntity: List<TaskEntity>)
}