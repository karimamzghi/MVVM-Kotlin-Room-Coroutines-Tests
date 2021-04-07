package io.kari.todo.repository

import androidx.lifecycle.LiveData


class TodoRepository(private val todoDao: TodoDao) {

    fun addItem(todo: Todo) {
        todoDao.insert(todo)
    }

    fun updateItem(todo: Todo) {
        todoDao.update(todo)
    }

    fun deleteItem(todo: Todo) {
        todoDao.delete(todo)
    }

    fun getTodoItems(): LiveData<List<Todo>> {
        return todoDao.query()
    }

}
