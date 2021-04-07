package io.kari.todo

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kari.todo.repository.Todo
import io.kari.todo.ui.MainActivity
import io.kari.todo.viewmodel.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("UNCHECKED_CAST")
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun shouldAddItem() {
        val vm: MainViewModel = mock()

        val items = MutableLiveData<List<Todo>>()
        whenever(vm.getTodoItems()).thenReturn(items)
        rule.launchActivity(null)

        onView(withId(R.id.fab)).perform(click())

        verify(vm).addItem(any())
    }

}
