package com.ezioapps.elitenotes.Db.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val title : String,
    var priority : Priority,
    val body : String

) : Parcelable