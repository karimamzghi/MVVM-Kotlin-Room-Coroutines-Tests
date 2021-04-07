package io.kari.todo.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "todos"
private const val DB_VERSION = 2

@Database(entities = [Todo::class], version = DB_VERSION)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun dao(): TodoDao

    companion object {
        @JvmStatic
        internal fun create(context: Context): TodoDatabase = Room.databaseBuilder(context,
                TodoDatabase::class.java, DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

}
