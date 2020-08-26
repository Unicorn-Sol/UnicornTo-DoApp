package com.ezioapps.elitenotes.Db.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import com.ezioapps.elitenotes.Db.TaskDao
import com.ezioapps.elitenotes.Db.models.Task

class TaskRepository(private val taskDao: TaskDao) {

    val getAllData : LiveData<List<Task>> = taskDao.getAllTasks()



    suspend fun insertTask(task : Task){
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun deleteAll(){
        taskDao.deleteAll()
    }

    fun searchDatabase(query: String): LiveData<List<Task>>{
        return taskDao.searchDatabase(query)
    }
}