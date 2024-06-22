package com.raphaeloliveira.taskbeat.di.module

import android.app.Application
import com.raphaeloliveira.taskbeat.TaskBeatApplication
import org.koin.dsl.module

val taskDatabaseModule = {app: Application -> module {
    single { (app as TaskBeatApplication).getDatabase().getTaskDao() }
}}