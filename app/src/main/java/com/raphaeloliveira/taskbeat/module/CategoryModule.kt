package com.raphaeloliveira.taskbeat.module

import android.app.Application
import com.raphaeloliveira.taskbeat.TaskBeatApplication
import org.koin.dsl.module

val categoryDatabaseModule = { app: Application -> module {
    single { (app as TaskBeatApplication).getDatabase().getCategoryDao() }
}}
