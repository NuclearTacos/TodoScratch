package io.nucleartacos.todoscratch

import android.support.v7.widget.RecyclerView
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
    var lastFocusedRow: Int? = null
    //var lastFocusedType: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTpe: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todolistitem_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setListId(todoListId)
        holder.updateView(position)

        var setLastFocused = { rowNumber: Int?, focusedType: String? ->
            lastFocusedRow = rowNumber
        }

        if (position != list.size) {
            var myTitleFocus = todoItemTitleFocus(list[position].id!!, setLastFocused)
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

            holder.checkBox.setOnLongClickListener {
                thread {
                    db.TodoItemDao().deleteById(list[position].id!!)
                }
                true
            }
        }
    }

    class todoItemTitleFocus(var itemId: Int, val setLastFocused: (Int?, String?) -> Unit ): View.OnFocusChangeListener {

        lateinit var holder: ViewHolder

        fun setMyHolder(incomingHolder: ViewHolder){
            holder = incomingHolder
        }

        override fun onFocusChange(view: View?, hasFocus: Boolean) {
            if (!hasFocus) {
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
            }  /*else {
                setLastFocused(holder.myPosition, FOCUSED_TITLE)
                Log.d("Butt", "Position: "+ holder.myPosition+" // FocusedType"+FOCUSED_TITLE)
            }*/
        }
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val editText = view.findViewById<TextView>(R.id.todoTitleEditText)
        val checkBox = view.findViewById<CheckBox>(R.id.isDoneCheckBox)
        val newItemButton = view.findViewById<Button>(R.id.newTodoButton)
        var todoListId: Int? = null
        var myPosition: Int? = null

        fun setListId(listId: Int) {
            todoListId = listId
        }

        fun updateView(index: Int) {
            myPosition = index

            /*if (index == lastFocusedRow) {
                editText.requestFocus()
            }// this block should take focus if the box was the last one focused before it rebound*/

            if (index != list.size) {
                editText.text = list[index].title
                checkBox.isChecked = list[index].isDone

                editText.visibility = View.VISIBLE
                checkBox.visibility = View.VISIBLE
                newItemButton.visibility = View.GONE
            } else {
                editText.visibility = View.GONE
                checkBox.visibility = View.GONE
                newItemButton.visibility = View.VISIBLE
                newItemButton.setOnClickListener {
                    thread{
                        db.TodoItemDao().getById(
                                db.TodoItemDao().insert(
                                        TodoItem(
                                                null,
                                                "",
                                                false, todoListId!!)
                                ).toInt()
                        )
                    }
                    //lastFocusedType = FOCUSED_BOX
                }
            }
        }
    }
}
