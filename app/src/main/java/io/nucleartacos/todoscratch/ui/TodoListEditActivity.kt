package io.nucleartacos.todoscratch.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.EditText
import io.nucleartacos.todoscratch.R
import io.nucleartacos.todoscratch.TodoListItemsAdapter
import io.nucleartacos.todoscratch.data.TodoList
import io.nucleartacos.todoscratch.db
import io.nucleartacos.todoscratch.viewmodels.ListItemsViewModel
import kotlin.concurrent.thread

class TodoListEditActivity : AppCompatActivity() {

    val TAG: String = "TodoListEditActivity: "

    lateinit var itemsRecyclerView: RecyclerView
    private var todoListId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist_edit)

        val titleEditText = findViewById<EditText>(R.id.listTitleEditText)

        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)

        todoListId = intent.getIntExtra(LIST_KEY, -1)
        Log.d("Butt", "TodoListEditActivity: todoListId: "+ todoListId)

        thread {
            var todoList = db.TodoListDao().getById(todoListId)

            titleEditText.setText(todoList.title)
            descriptionEditText.setText(todoList.description)

        }

        if (titleEditText.text.toString() == "") {
                titleEditText.requestFocus()
                Log.d("Butt", "Focus Requested by title edit text")
        }

        var todoListEditFocus = todoListFocus(todoListId)
        todoListEditFocus.setEditTexts(titleEditText, descriptionEditText)

        titleEditText.setOnFocusChangeListener(todoListEditFocus)
        descriptionEditText.setOnFocusChangeListener(todoListEditFocus)


        itemsRecyclerView = findViewById(R.id.todoListItemsView)
        itemsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val myAdapter = TodoListItemsAdapter(todoListId)
        itemsRecyclerView.adapter = myAdapter


        /*saveListButton.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            saveListChanges(titleEditText, descriptionEditText)
            Log.d("butt", "Toasty")
            *//*val intent = Intent(this, TodoListEditActivity::class.java)
            startActivity(intent)*//*
        }*/

        ViewModelProviders.of(this)
                .get(ListItemsViewModel::class.java)
                .getTodoItems(todoListId)
                .observe(this, Observer {
                    if (it != null) {
                        myAdapter.list.clear()
                        myAdapter.list.addAll(it)
                        myAdapter.notifyDataSetChanged()
                    }
                    //Log.d("Butt","dataset changed")
                })
    } //onCreate

    /*fun saveListChanges(titleEditText: EditText, descriptionEditText: EditText) {
         Log.d("butt","Toasted: "+ todoListId)
         var todoList: TodoList = if (todoListId != -1){
             TodoList(
                 todoListId,
                 titleEditText.text.toString(),
                 descriptionEditText.text.toString()
             )
         } else {
             TodoList(
                 null,
                 titleEditText.text.toString(),
                 descriptionEditText.text.toString()
             )
         }
         thread {
             //var insertedList: Int = db.TodoListDao().insert(todoList).toInt() // created simplified form. should delte if underline works
             db.TodoListDao().insert(todoList)

             // You neeed to save back the current state of the title/description to the itemsList.  Rememmber you ahve access to the id of the itemsList
         }
     }*/

    class todoListFocus(var listId: Int): View.OnFocusChangeListener {
        lateinit var title: EditText
        lateinit var description: EditText

        fun setEditTexts(titleEditText: EditText, descriptionEditText: EditText) {
            title = titleEditText
            description = descriptionEditText
        }

        override fun onFocusChange(view: View?, hasFocus: Boolean) {
            if (!hasFocus) {
                thread {
                    //var oldItem = db.TodoListDao().getById(listId)
                    db.TodoListDao().insert(
                            TodoList(
                                    listId,
                                    title.text.toString(),
                                    description.text.toString()
                            )
                    )
                }
                Log.d("Butt", "Focus Changed")
            }
        }
    }
}
