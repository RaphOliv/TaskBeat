package com.raphaeloliveira.taskbeat.usecase.category

import com.raphaeloliveira.taskbeat.data.CategoryDao
import com.raphaeloliveira.taskbeat.data.TaskDao
import com.raphaeloliveira.taskbeat.domain.CategoryEntity

class DeleteCategoryUseCase (private val categoryDao: CategoryDao, private val taskDao: TaskDao) {
    suspend fun execute(category: CategoryEntity) {
        val tasksToBeDeleted = taskDao.getAllByCategoryName(category.name)
        taskDao.deleteAll(tasksToBeDeleted)
        categoryDao.delete(category)
    }
}