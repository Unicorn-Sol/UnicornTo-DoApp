package com.ezioapps.elitenotes.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ezioapps.elitenotes.Db.models.Task
import kotlinx.coroutines.selects.select
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task : Task)

    @Query("select * from Task order by id desc")
    fun getAllTasks() : LiveData<List<Task>>

    @Update
    suspend fun updateTask(task : Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("delete from Task")
    suspend fun deleteAll()


    @Query("select * from Task where title like :query")
    fun searchDatabase(query: String) : LiveData<List<Task>>

    @Query("select * from Task order by case when priority like 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun fromHighToLow() : LiveData<List<Task>>

    @Query("select * from Task order by case when priority like 'L%' then 1 when priority like 'M%' then 2 when priority like 'H%' then 3 end")
    fun fromLowToHigh() : LiveData<List<Task>>
}
