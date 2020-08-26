package com.ezioapps.elitenotes.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ezioapps.elitenotes.Db.models.Priority
import com.ezioapps.elitenotes.Db.models.Task
import com.ezioapps.elitenotes.R

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase : MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseIsEmpty(tasks : List<Task>){
        emptyDatabase.value = tasks.isEmpty()
    }

   val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
       override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
           when (position) {
               0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red)) }
               1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow)) }
               2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green)) }
           }
       }
       override fun onNothingSelected(parent: AdapterView<*>?) {
       }

   }
    fun verify(title : String, body : String): Boolean{
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(body))
    }

    fun parseToPr(priority: String): Priority {

        return when(priority){
            "High Priority" -> {
                Priority.HiGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> Priority.LOW
        }
    }
}