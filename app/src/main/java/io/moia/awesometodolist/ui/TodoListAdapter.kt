package io.moia.awesometodolist.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.moia.awesometodolist.R
import io.moia.awesometodolist.repository.Todo
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoListAdapter(todoItemClickListener: TodoItemClickListener) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TodoItemCallback()) {

    private val listener: TodoItemClickListener = todoItemClickListener

    interface TodoItemClickListener {
        fun onItemClicked(todo: Todo)
        fun onItemDeleted(todo: Todo)
        fun onItemChecked(todo: Todo)
    }


    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TodoViewHolder, pos: Int) {
        val item = getItem(pos)
        viewHolder.bind(item, listener)
    }


    private class TodoItemCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(item1: Todo, item2: Todo): Boolean {
            return item1.id == item2.id
        }

        override fun areContentsTheSame(item1: Todo, item2: Todo): Boolean {
            return item1 == item2
        }

    }


    class TodoViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @SuppressLint("SetTextI18n")

        override fun bind(todo: Todo, listener: TodoItemClickListener) = with(itemView) {
            itemView.text.text = todo.text
            itemView.cb_todo_check.isChecked = todo.completed

            applyStrikeThruText(todo.completed)

            itemView.setOnClickListener {
                listener.onItemClicked(todo)
            }
            itemView.btn_delete.setOnClickListener {
                listener.onItemDeleted(todo)
            }
            itemView.cb_todo_check.setOnCheckedChangeListener { _, isChecked ->
                applyStrikeThruText(isChecked)
                listener.onItemChecked(todo)
            }
        }

        private fun applyStrikeThruText(isChecked: Boolean) {
            if (isChecked)
                itemView.text.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                itemView.text.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        open fun bind(todo: Todo, listener: TodoItemClickListener) = with(itemView) {
        }
    }
}
