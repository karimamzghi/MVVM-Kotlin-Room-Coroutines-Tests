package io.moia.awesometodolist.repository

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract class TodoDao {

    @Query("SELECT * FROM todo")
    abstract fun query(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo")
    abstract fun get(): Todo

    @Insert
    abstract fun insert(todo: Todo)

    @Update
    abstract fun update(todo: Todo)

    @Delete
    abstract fun delete(todo: Todo)

}
