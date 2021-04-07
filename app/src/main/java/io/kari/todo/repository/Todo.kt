package io.kari.todo.repository

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo")
@Parcelize()
data class Todo(
        @PrimaryKey
        val id: String,
        var text: String,
        var completed: Boolean
) : Parcelable
