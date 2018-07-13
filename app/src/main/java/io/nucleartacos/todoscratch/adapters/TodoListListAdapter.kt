package io.nucleartacos.todoscratch.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.nucleartacos.todoscratch.R
import io.nucleartacos.todoscratch.data.TodoList

class TodoListListAdapter() : RecyclerView.Adapter<TodoListListAdapter.ViewHolder>() {
    val list = mutableListOf<TodoList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTpe: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolist_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.editText.text = list[position].title
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
      /*  val editText = view.findViewById<TextView>(R.id.todoTitleEditText)
        val checkBox = view.findViewById<CheckBox>(R.id.isDoneCheckBox)*/
// oops, I included this form the wrong thing I was working on
    }
}