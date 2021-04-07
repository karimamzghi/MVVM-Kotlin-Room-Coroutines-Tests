package io.kari.todo.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.kari.todo.repository.Todo
import io.kari.todo.repository.TodoDatabase
import io.kari.todo.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: TodoRepository) : ViewModel() {


    fun addItem(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            repository.addItem(todo)
        }
    }

    fun updateItem(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            repository.updateItem(todo)
        }
    }

    fun deleteItem(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            repository.deleteItem(todo)
        }
    }

    fun getTodoItems(): LiveData<List<Todo>> {
        return repository.getTodoItems()
    }

    fun completeItem(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            todo.completed = !todo.completed
            repository.updateItem(todo)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class TodoViewModelFactory(private val todoRepository: TodoRepository) :
        ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: TodoViewModelFactory? = null

        fun getInstance(context: Context): TodoViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: TodoViewModelFactory(TodoRepository(TodoDatabase.create(context).dao()))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(todoRepository) as T
    }
}
