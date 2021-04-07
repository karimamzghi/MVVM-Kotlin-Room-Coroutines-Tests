package io.kari.todo.data


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.runner.AndroidJUnit4
import io.kari.todo.repository.Todo
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
open class TodoDaoTest : BaseAppDatabaseTest() {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val dao by lazy { db.dao() }


    @Test
    fun insertTodoTest() {
        val todo = Todo(id = "12245", text = "Finish MOIA tech test", completed = false)
        val insertedTodo = dao.insert(todo)

        assertNotNull(insertedTodo)
    }

    @Test
    fun updateTodoTest() {
        val todo = Todo(id = "1213", text = "Hello World", completed = false)
        dao.insert(todo)

        todo.text = "Bye bye World"
        dao.update(todo)

        assertEquals(dao.query().getValueBlocking()?.get(0)?.text, "Bye bye World")
    }

    @Test
    fun deleteTodoTest() {
        val todo = Todo(id = "34766", text = "delete todo item", completed = false)
        dao.insert(todo)

        var todoFromDb = dao.get()
        dao.delete(todoFromDb)

        todoFromDb = dao.get()
        assertNull(todoFromDb)
    }

    @Test
    fun checkTodoTest() {
        val todo = Todo(id = "56646", text = "complete todo item", completed = false)
        dao.insert(todo)

        todo.completed = true
        dao.update(todo)

        assertEquals(dao.query().getValueBlocking()?.get(0)?.completed, true)
    }

    @Throws(InterruptedException::class)
    fun <T> LiveData<T>.getValueBlocking(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}
