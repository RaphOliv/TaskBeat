package com.raphaeloliveira.taskbeat.usecase.task

import com.raphaeloliveira.taskbeat.data.TaskDao
import com.raphaeloliveira.taskbeat.domain.TaskEntity

class UpdateTaskUseCase (private val taskDao: TaskDao) {
    suspend fun execute(task: TaskEntity) {
        taskDao.update(task)
    }
}