package com.ezioapps.elitenotes.Db

import android.content.Context

import androidx.room.*
import com.ezioapps.elitenotes.Db.models.Task


@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(com.ezioapps.elitenotes.Db.TypeConverter::class)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun getTaskDao() : TaskDao

    companion object{

        @Volatile private var instance : TaskDatabase? = null

        operator fun invoke(context: Context): TaskDatabase {
            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, "myDB").build()
                }
            }
            return instance!!
        }


    }
}