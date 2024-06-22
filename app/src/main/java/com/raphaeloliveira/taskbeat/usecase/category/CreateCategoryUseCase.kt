package com.raphaeloliveira.taskbeat.usecase.category

import com.raphaeloliveira.taskbeat.data.CategoryDao
import com.raphaeloliveira.taskbeat.domain.CategoryEntity

class CreateCategoryUseCase (private val categoryDao: CategoryDao){
    suspend fun execute(category: CategoryEntity) {
        categoryDao.insert(category)
    }
}