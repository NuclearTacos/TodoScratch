package io.nucleartacos.todoscratch

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import io.nucleartacos.todoscratch.data.TodoItem
import kotlin.concurrent.thread

class TodoListItemsAdapter(val todoListId: Int) : RecyclerView.Adapter<TodoListItemsAdapter.ViewHolder>() {
    val list = mutableListOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTpe: Int): ViewHolder {
       /* thread {
            var itemsFromList = db.TodoItemDao().getByListId(todoListId)
            for (item in itemsFromList) list.add(item)
        }*/ // This was here, but I don't think it's necessary
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolistitem_row, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setListId(todoListId)
        holder.updateView(position)
        if (position != list.size) {
            var myTitleFocus = todoTitleFocus(list[position].id!!)
            myTitleFocus.setMyHolder(holder)

            holder.editText.setOnFocusChangeListener(myTitleFocus)
            holder.checkBox.setOnClickListener { View ->
                thread {
                    var oldItem = db.TodoItemDao().getById(list[position].id!!)
                    db.TodoItemDao().insert(
                            TodoItem(
                                    oldItem.id,
                                    oldItem.title,
                                    holder.checkBox.isChecked,
                                    oldItem.todoListId
                            )
                    )
                }
            }
        }
    }

    class todoTitleFocus(var itemId: Int): View.OnFocusChangeListener {

        lateinit var holder: ViewHolder

        fun setMyHolder(incomingHolder: ViewHolder){
            holder = incomingHolder
        }

        override fun onFocusChange(view: View?, hasFocus: Boolean) {
            if (!hasFocus) {
                Log.d("Butt", "Lost Focus: Text is :" + holder.editText.text.toString())
                thread {
                    var oldItem = db.TodoItemDao().getById(itemId)
                    db.TodoItemDao().insert(
                            TodoItem(
                                    oldItem.id,
                                    holder.editText.text.toString(),
                                    oldItem.isDone,
                                    oldItem.todoListId
                            )
                    )
                }
            }
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val editText = view.findViewById<TextView>(R.id.todoTitleEditText)
        val checkBox = view.findViewById<CheckBox>(R.id.isDoneCheckBox)
        val newItemButton = view.findViewById<Button>(R.id.newTodoButton)
        var todoListId: Int? = null

        fun setListId(listId: Int) {
            todoListId = listId
        }

        fun updateView(index: Int) {
            if (index != list.size) {
                editText.text = list[index].title
                checkBox.isChecked = list[index].isDone

                editText.visibility = View.VISIBLE
                checkBox.visibility = View.VISIBLE
                newItemButton.visibility = View.GONE

                //checkBox.set

                checkBox.setOnClickListener() {
                    // launchCreatorActivity(list[index].id) // replace this with what clicking the checkbox should do.
                }
            } else {
                editText.visibility = View.GONE
                checkBox.visibility = View.GONE
                newItemButton.visibility = View.VISIBLE
                newItemButton.setOnClickListener() {
                    thread{
                        list.add(
                                db.TodoItemDao().getById(
                                        db.TodoItemDao().insert(
                                                TodoItem(
                                                        null,
                                                        "",
                                                        false, todoListId!!)
                                        ).toInt()
                                )
                        )
                    }
                }
            }
        }

    }
}
