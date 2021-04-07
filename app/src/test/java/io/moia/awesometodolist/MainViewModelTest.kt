package io.moia.awesometodolist


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.verify
import io.moia.awesometodolist.repository.Todo
import io.moia.awesometodolist.repository.TodoDao
import io.moia.awesometodolist.repository.TodoRepository
import io.moia.awesometodolist.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TodoRepository

    @Mock
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var dao: TodoDao


    val todo1 = Todo(id = "12245", text = "first todo", completed = false)
    val todo2 = Todo(id = "12246", text = "second todo", completed = true)
    val todos = spy(mutableListOf(todo1, todo2))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = TodoRepository(dao)
        viewModel = MainViewModel(repository)
    }


    @Test
    fun `should add item1`() {
        //given
        val todo = Todo(id = "1225", text = "insert todo", completed = true)

        //when
        val insertedTodo = viewModel.addItem(todo)

        //than
        verify(dao).insert(todo)
        Assert.assertNotNull(insertedTodo)
    }
}
