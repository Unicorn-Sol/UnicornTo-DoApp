package com.ezioapps.elitenotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ezioapps.elitenotes.Db.TaskDatabase
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.Db.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {


    private val taskDao = TaskDatabase(application).getTaskDao()
    private val repository : TaskRepository
    val getAllData : LiveData<List<Task>>

    init{
        repository = TaskRepository(taskDao)
        getAllData = repository.getAllData
    }

    fun insertTask(task: Task){

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }
    fun updateTask(task : Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(query: String) : LiveData<List<Task>>{
        return repository.searchDatabase(query)
    }
}