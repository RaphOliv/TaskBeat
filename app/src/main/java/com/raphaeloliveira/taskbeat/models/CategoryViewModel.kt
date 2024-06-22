package com.raphaeloliveira.taskbeat.models

import androidx.lifecycle.ViewModel
import com.raphaeloliveira.taskbeat.TaskBeatApplication
import com.raphaeloliveira.taskbeat.data.CategoryDao
import com.raphaeloliveira.taskbeat.data.TaskDao

/*
class CategoryViewModel(private val categoryDao: CategoryDao, private val taskDao: TaskDao) : ViewModel() {

    companion object {
        fun create(application: TaskBeatApplication): CategoryViewModel {
            val taskBeatApplication =(application as TaskBeatApplication).getDatabase()
            val categorydao = taskBeatApplication.getCategoryDao()
            val taskdao = taskBeatApplication.getTaskDao()
            return CategoryViewModel(categorydao, taskdao)
        }
    }
}*/
