package com.ezioapps.elitenotes.Db

import androidx.room.TypeConverter
import com.ezioapps.elitenotes.Db.models.Priority

class TypeConverter {

    @TypeConverter
    fun convertToString(priority: Priority) : String{
        return priority.name
    }

    @TypeConverter
    fun convertToEnum(priority: String) : Priority {
        return Priority.valueOf(priority)
    }
}