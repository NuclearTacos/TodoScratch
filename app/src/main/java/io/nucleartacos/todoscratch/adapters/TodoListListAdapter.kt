package io.nucleartacos.todoscratch.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.nucleartacos.todoscratch.R
import io.nucleartacos.todoscratch.data.TodoList
import io.nucleartacos.todoscratch.db
import kotlin.concurrent.thread

class TodoListListAdapter(val launchTodoListEditActivity: (Int?) -> Unit) : RecyclerView.Adapter<TodoListListAdapter.ViewHolder>() {
    val list = mutableListOf<TodoList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTpe: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_row, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.todoListNameLabel)
        val descriptionTextView = view.findViewById<TextView>(R.id.todoListDescriptionLabel)

        fun updateView(index: Int) {
            titleTextView.text = list[index].title
            descriptionTextView.text = list[index].description


            view.setOnClickListener {
                launchTodoListEditActivity(list[index].id)
                true
            }

            view.setOnLongClickListener{
                db.TodoItemDao().deleteByListId(list[index].id!!)
                db.TodoListDao().deleteById(list[index].id!!)
                true
            }
        }
    }
}