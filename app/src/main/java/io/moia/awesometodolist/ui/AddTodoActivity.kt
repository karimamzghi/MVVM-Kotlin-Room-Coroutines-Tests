package io.moia.awesometodolist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.moia.awesometodolist.R
import io.moia.awesometodolist.repository.Todo
import kotlinx.android.synthetic.main.activity_add_todo.*
import java.util.*

const val KEY_INTENT = "KEY_INTENT"

class AddTodoActivity : AppCompatActivity() {

    var item: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val intent = intent
        if (intent != null && intent.hasExtra(KEY_INTENT)) {
            val item: Todo = intent.getParcelableExtra<Todo>(KEY_INTENT)
            this.item = item

            initTodoEditText(item)
        }

        updateTitle()
        saveTodoItem()
    }

    private fun saveTodoItem() {
        btn_done.setOnClickListener {
            if (validateFields()) {
                val id = if (item != null) item?.id else UUID.randomUUID().toString()
                val todo = Todo(
                        id = id.toString(),
                        text = et_todo_title.text.toString(),
                        completed = item?.completed ?: false
                )

                val intent = Intent()
                intent.putExtra(KEY_INTENT, todo)
                setResult(RESULT_OK, intent)

                finish()
            }
        }
    }

    private fun validateFields(): Boolean {
        if (et_todo_title.text.isNullOrBlank()) {
            tf_todo_title.error = getString(R.string.message_enter_todo)
            et_todo_title.requestFocus()
            return false
        }

        Toast.makeText(this, getString(R.string.message_save_todo), Toast.LENGTH_SHORT).show()
        return true
    }

    private fun updateTitle() {
        title = if (item != null) getString(R.string.edit_todo) else getString(R.string.add_todo)
    }

    private fun initTodoEditText(item: Todo) {
        et_todo_title.setText(item.text, TextView.BufferType.EDITABLE)
    }

}
