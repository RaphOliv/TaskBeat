package com.raphaeloliveira.taskbeat.di

import com.raphaeloliveira.taskbeat.di.module.categoryDatabaseModule
import com.raphaeloliveira.taskbeat.di.module.taskDatabaseModule

val appModules = listOf(
    categoryDatabaseModule,
    taskDatabaseModule
)