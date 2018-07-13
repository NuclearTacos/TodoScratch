package io.nucleartacos.todoscratch

import android.support.design.R.id.checkbox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import io.nucleartacos.todoscratch.data.TodoItem
import io.nucleartacos.todoscratch.data.TodoList
import kotlin.concurrent.thread

class TodoListItemsAdapter() : RecyclerView.Adapter<TodoListItemsAdapter.ViewHolder>() {
    val list = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTpe: Int): ViewHolder {
        thread {
            for (item in db.TodoItemDao().getAll() ) list.add(item)
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_row, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val editText = view.findViewById<TextView>(R.id.todoTitleEditText)
        val checkBox = view.findViewById<CheckBox>(R.id.isDoneCheckBox)

        fun updateView(index: Int) {
            if (index != list.size) {
                editText.text = list[index].title
                checkBox.isChecked = list[index].isDone
                checkBox.setOnClickListener() {
                    // launchCreatorActivity(list[index].id) // replace this with what clicking the checkbox should do.
                }
            } else {
                checkBox.setOnClickListener() {
                    // launchCreatorActivity(list[index].id) // replace this with what clicking the checkbox should do.
                }
            }
        }

    }
}