package com.raphaeloliveira.taskbeat.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphaeloliveira.taskbeat.TaskBeatApplication
import org.koin.dsl.module

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categoryentity")
    fun getAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryEntity: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryEntity: CategoryEntity)

    @Delete
    fun delete(categoryEntity: CategoryEntity)

}
