package com.mathpresso.nunu.crashrxandroid.datasource

import com.mathpresso.nunu.crashrxandroid.model.Task

class TaskDataSource {
    companion object {
        fun getTasksList() = listOf(
            Task(description = "Take", isComplete = true, priority = 3),
            Task(description = "Walk", isComplete = false, priority = 2),
            Task(description = "Make", isComplete = true, priority = 0),
            Task(description = "Cook", isComplete = false, priority = 1),
            Task(description = "Swim", isComplete = true, priority = 5),
        )
    }
}