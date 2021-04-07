package io.moia.awesometodolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.moia.awesometodolist.R
import io.moia.awesometodolist.repository.Todo
import io.moia.awesometodolist.viewmodel.MainViewModel
import io.moia.awesometodolist.viewmodel.TodoViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

const val INTENT_CREATE_TODO_ITEM = 1
const val INTENT_EDIT_TODO_ITEM = 2

class MainActivity : AppCompatActivity(), TodoListAdapter.TodoItemClickListener {

    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var vm: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = TodoViewModelFactory.getInstance(applicationContext)
        vm = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        initToolbar()
        initTodoListAdapter()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val item = data?.getParcelableExtra<Todo>(KEY_INTENT)!!
            when (requestCode) {
                INTENT_CREATE_TODO_ITEM -> {
                    vm.addItem(item)
                }

                INTENT_EDIT_TODO_ITEM -> {
                    vm.updateItem(item)
                }
            }
        }
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initTodoListAdapter() {
        todoListAdapter = TodoListAdapter(this)
        list.adapter = todoListAdapter
    }

    private fun observeViewModel() {
        fab.setOnClickListener {
            launchAddTodoItem()
        }

        vm.getTodoItems().observe(this, Observer {
            displayEmptyTodoListView(it.isEmpty())
            todoListAdapter.submitList(it)
        })
    }

    private fun displayEmptyTodoListView(visibility: Boolean) {
        if (visibility) {
            tv_empty_todo_list.visibility = View.VISIBLE
            iv_empty_todo_list.visibility = View.VISIBLE
        } else {
            tv_empty_todo_list.visibility = View.GONE
            iv_empty_todo_list.visibility = View.GONE
        }
    }

    private fun launchAddTodoItem(){
        val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
        startActivityForResult(intent, INTENT_CREATE_TODO_ITEM)
    }

    private fun launchEditTodoItem(todo: Todo){
        val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
        intent.putExtra(KEY_INTENT, todo)
        startActivityForResult(intent, INTENT_EDIT_TODO_ITEM)
    }

    override fun onItemClicked(todo: Todo) {
       launchEditTodoItem(todo)
    }

    override fun onItemDeleted(todo: Todo) {
        vm.deleteItem(todo)
    }

    override fun onItemChecked(todo: Todo) {
        vm.completeItem(todo)
    }
}
